package com.example.androidpractice.data

import com.example.androidpractice.domain.model.CategoryFilter
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsApi
import com.example.androidpractice.domain.repo.EventsRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepoImpl @Inject constructor(
    private val eventsApi: EventsApi
) : EventsRepo {

    private val _readEvents: Channel<String?> = Channel()
    override val readEvents: ReceiveChannel<String?> = _readEvents

    private val _events = MutableStateFlow<List<Event>?>(null)
    override val events: StateFlow<List<Event>?> = _events

    override fun updateCachedEvents(events: List<Event>) {
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
            (event.categories intersect checkedCategories.toSet()).isNotEmpty()
        } ?: listOf()
        filteredEvents.size - filteredEvents.filter { it.id in readEvents }.size
    }

    override suspend fun readEvent(eventId: String) {
        _readEvents.send(eventId)
    }

    override suspend fun searchEventsByOrganization(query: String): SearchResult {
        return eventsApi.searchByOrganization(query).blockingFirst()
    }

    override suspend fun searchEventsByName(query: String): SearchResult {
        return eventsApi.searchByEvent(query).blockingFirst()
    }
}
