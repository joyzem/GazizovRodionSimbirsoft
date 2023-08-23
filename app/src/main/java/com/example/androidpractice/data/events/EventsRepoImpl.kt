package com.example.androidpractice.data.events

import android.content.res.AssetManager
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
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepoImpl @Inject constructor(
    private val eventsApi: EventsRetrofitApi,
    private val assetManager: AssetManager,
) : EventsRepo {

    private val _readEvents: Channel<String?> = Channel()
    override val readEvents: ReceiveChannel<String?> = _readEvents

    private val _events = MutableStateFlow<List<Event>?>(null)
    override val events: StateFlow<List<Event>?> = _events

    override fun searchEventByName(query: String): Observable<SearchResult> {
        return fetchEvents().map { events ->
            events.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }.map { events ->
            SearchResult(
                id = UUID.randomUUID().toString(),
                keywords = events.map {
                    it.title
                }.toSet().toList(),
                events = events
            )
        }
    }

    override fun searchEventByOrganizationName(query: String): Observable<SearchResult> {
        return fetchEvents().map { events ->
            events.filter {
                it.sponsor.contains(query, ignoreCase = true)
            }
        }.map { events ->
            SearchResult(
                UUID.randomUUID().toString(),
                events.map { it.sponsor }.toSet().toList(),
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

    override fun fetchEvents(): Observable<List<Event>> {
        return eventsApi.fetchEvents()
            .map { dtos ->
                dtos.map { dto ->
                    dto.toModel()
                }
            }
            .onErrorReturnItem(getEventsFromFile())
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
