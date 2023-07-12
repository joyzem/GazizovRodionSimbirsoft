package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.model.SearchResult
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.StateFlow

interface EventsRepo {
    val events: StateFlow<List<Event>?>

    fun updateCachedEvents(events: List<Event>)

    fun searchEventsByOrganization(query: String): Observable<SearchResult>

    fun searchEventsByName(query: String): Observable<SearchResult>
}
