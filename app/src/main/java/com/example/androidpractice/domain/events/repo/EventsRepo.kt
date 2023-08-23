package com.example.androidpractice.domain.events.repo

import com.example.androidpractice.domain.categories.model.CategoryFilter
import com.example.androidpractice.domain.events.model.Event
import com.example.androidpractice.domain.search.model.SearchResult
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EventsRepo {
    val events: Flow<List<Event>?>
    val readEvents: ReceiveChannel<String?>

    fun unreadNewsCounter(
        readEvents: StateFlow<List<String>>,
        appliedFilters: StateFlow<List<CategoryFilter>?>,
        allEvents: Flow<List<Event>?>
    ): Flow<Int>

    fun searchEventByName(query: String): Flow<SearchResult>

    fun searchEventByOrganizationName(query: String): Flow<SearchResult>

    suspend fun fetchEvents()

    suspend fun readEvent(eventId: String)
}
