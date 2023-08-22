package com.example.androidpractice.domain.events.api

import com.example.androidpractice.domain.events.model.Event
import com.example.androidpractice.domain.search.model.SearchResult
import io.reactivex.rxjava3.core.Observable

interface EventsApi {

    fun searchByEvent(query: String): Observable<SearchResult>

    fun searchByOrganization(query: String): Observable<SearchResult>

    fun fetchEvents(): Observable<List<Event>>
}