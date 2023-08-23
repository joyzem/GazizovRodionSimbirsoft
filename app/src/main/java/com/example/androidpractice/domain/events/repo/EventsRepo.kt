package com.example.androidpractice.domain.events.repo

import com.example.androidpractice.domain.categories.model.CategoryFilter
import com.example.androidpractice.domain.events.model.Event
import com.example.androidpractice.domain.search.model.SearchResult
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EventsRepo {
    val events: StateFlow<List<Event>?>
    val readEvents: ReceiveChannel<String?>

    fun setEvents(events: List<Event>)

    fun unreadNewsCounter(
        readEvents: StateFlow<List<String>>,
        appliedFilters: StateFlow<List<CategoryFilter>?>,
        allEvents: StateFlow<List<Event>?>
    ): Flow<Int>

    fun fetchEvents(): Observable<List<Event>>

    fun searchEventByName(query: String): Observable<SearchResult>

    fun searchEventByOrganizationName(query: String): Observable<SearchResult>

    suspend fun readEvent(eventId: String)
}
