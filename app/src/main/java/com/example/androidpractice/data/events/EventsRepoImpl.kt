package com.example.androidpractice.data.events

import android.content.res.AssetManager
import com.example.androidpractice.data.categories.network.CategoriesRetrofitApi
import com.example.androidpractice.data.events.dto.EventDTO
import com.example.androidpractice.data.events.dto.toModel
import com.example.androidpractice.data.events.network.EventsRetrofitApi
import com.example.androidpractice.domain.categories.model.CategoryFilter
import com.example.androidpractice.domain.events.model.Event
import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.domain.search.model.SearchResult
import com.example.androidpractice.util.json.fromJson
import com.example.androidpractice.util.json.getJsonFromAssets
import com.google.gson.GsonBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepoImpl @Inject constructor(
    private val eventsApi: EventsRetrofitApi,
    private val categoriesRetrofitApi: CategoriesRetrofitApi,
    private val assetManager: AssetManager,
) : EventsRepo {

    private val _readEvents: Channel<String?> = Channel()
    override val readEvents: ReceiveChannel<String?> = _readEvents

    private val _events = MutableStateFlow<List<Event>?>(null)
    override val events: StateFlow<List<Event>?> = _events

    override fun searchEventByName(query: String): Flow<SearchResult> {
        return fetchEvents().map { events ->
            events.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }.map { events ->
            val ids = events.map { it.category }
            val keywords = categoriesRetrofitApi.fetchCategories().filter {
                it.id in ids
            }.map {
                it.name
            }
            SearchResult(
                UUID.randomUUID().toString(),
                keywords = keywords,
                events
            )
        }
    }

    override fun searchEventByOrganizationName(query: String): Flow<SearchResult> {
        return fetchEvents().map { events ->
            events.filter {
                it.sponsor.contains(query, ignoreCase = true)
            }
        }.map { events ->
            val ids = events.map { it.category }
            val keywords = categoriesRetrofitApi.fetchCategories().filter {
                it.id in ids
            }.map {
                it.name
            }
            SearchResult(
                UUID.randomUUID().toString(),
                keywords = keywords,
                events
            )
        }
    }

    override fun setEvents(events: List<Event>) {
        _events.update {
            events
        }
    }

    override fun unreadNewsCounter(
        readEvents: StateFlow<List<String>>,
        appliedFilters: StateFlow<List<CategoryFilter>?>,
        allEvents: StateFlow<List<Event>?>
    ) = combine(
        readEvents,
        appliedFilters,
        allEvents
    ) { readEvents, filters, events ->
        val filteredEvents = events?.filter { event ->
            val checkedCategories = filters?.filter {
                it.checked
            }?.map { it.category } ?: listOf()
            (event.category in checkedCategories.map { it.id })
        } ?: listOf()
        filteredEvents.size - filteredEvents.filter { it.id in readEvents }.size
    }

    override fun fetchEvents(): Flow<List<Event>> {
        return flow {
            val events = eventsApi.fetchEvents().map { dto ->
                dto.toModel()
            }
            emit(events)
        }.catch {
            emit(getEventsFromFile())
        }
    }

    override suspend fun readEvent(eventId: String) {
        _readEvents.send(eventId)
    }

    private fun getEventsFromFile(): List<Event> {
        val json = getJsonFromAssets(assetManager, "events.json")
        val gson = GsonBuilder()
            .create()
        val eventsDto = gson.fromJson<List<EventDTO>>(json)
        return eventsDto.map {
            it.toModel()
        }
    }
}
