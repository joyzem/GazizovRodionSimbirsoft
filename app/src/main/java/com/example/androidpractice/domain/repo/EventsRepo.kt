package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.CategoryFilter
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.model.SearchResult
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EventsRepo {
    val events: StateFlow<List<Event>?>
    val readEvents: ReceiveChannel<String?>

    fun updateCachedEvents(events: List<Event>)

    fun unreadNewsCounter(
        readEvents: StateFlow<List<String>>,
        appliedFilters: StateFlow<List<CategoryFilter>?>,
        allEvents: StateFlow<List<Event>?>
    ): Flow<Int>

    suspend fun searchEventsByOrganization(query: String): SearchResult

    suspend fun searchEventsByName(query: String): SearchResult

    suspend fun readEvent(eventId: String)
}
