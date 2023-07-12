package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.SearchResult
import io.reactivex.rxjava3.core.Observable

interface EventsApi {

    fun searchByEvent(query: String): Observable<SearchResult>

    fun searchByOrganization(query: String): Observable<SearchResult>
}