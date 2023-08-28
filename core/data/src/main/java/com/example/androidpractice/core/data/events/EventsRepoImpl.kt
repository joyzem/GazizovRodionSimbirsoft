package com.example.androidpractice.core.data.events

import android.content.res.AssetManager
import com.example.androidpractice.core.database.events.EventsDao
import com.example.androidpractice.core.database.events.toEntity
import com.example.androidpractice.core.database.events.toModel
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.core.model.category.CategoryFilter
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.core.model.search.SearchResult
import com.example.androidpractice.core.network.categories.CategoriesRetrofitService
import com.example.androidpractice.core.network.events.EventDTO
import com.example.androidpractice.core.network.events.EventsRetrofitService
import com.example.androidpractice.core.network.events.toModel
import com.example.utils.json.fromJson
import com.example.utils.json.getJsonFromAssets
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class EventsRepoImpl @Inject constructor(
    private val eventsApi: EventsRetrofitService,
    private val categoriesRetrofitApi: CategoriesRetrofitService,
    private val eventsDao: EventsDao,
    private val assetManager: AssetManager
) : EventsRepo {

    private val _readEvents: Channel<String?> = Channel()
    override val readEvents: ReceiveChannel<String?> = _readEvents

    private var eventsFetched: Boolean = false
    override val events: Flow<List<Event>?> = eventsDao.getEvents().map { entities ->
        entities.map {
            it.toModel()
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {
            emit(getEventsFromFile())
        }

    override fun searchEventByName(query: String): Flow<SearchResult> {
        return flow {
            val events = eventsApi.fetchEvents().filter {
                it.name.contains(query, ignoreCase = true)
            }.map {
                it.toModel()
            }
            val categories = categoriesRetrofitApi.fetchCategories()
            val keywords = categories.filter { category ->
                category.id in events.map { it.category }
            }.map {
                it.name
            }
            emit(
                SearchResult(
                    UUID.randomUUID().toString(),
                    keywords = keywords,
                    events = events
                )
            )
        }
    }

    override fun searchEventByOrganizationName(query: String): Flow<SearchResult> {
        return flow {
            val events = eventsApi.fetchEvents().filter {
                it.organisation.contains(query, ignoreCase = true)
            }.map {
                it.toModel()
            }
            val categories = categoriesRetrofitApi.fetchCategories()
            val keywords = categories.filter { category ->
                category.id in events.map { it.category }
            }.map {
                it.name
            }
            emit(
                SearchResult(
                    UUID.randomUUID().toString(),
                    keywords = keywords.toList(),
                    events = events
                )
            )
        }
    }

    override fun unreadNewsCounter(
        readEvents: StateFlow<List<String>>,
        appliedFilters: StateFlow<List<CategoryFilter>?>,
        allEvents: Flow<List<Event>?>
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

    override suspend fun fetchEvents() {
        if (eventsFetched) return
        val events = try {
            eventsApi.fetchEvents().map { dto ->
                dto.toModel()
            }
        } catch (e: Exception) {
            getEventsFromFile()
        }.map {
            it.toEntity()
        }
        eventsDao.insertEvents(events)
        eventsFetched = true
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
