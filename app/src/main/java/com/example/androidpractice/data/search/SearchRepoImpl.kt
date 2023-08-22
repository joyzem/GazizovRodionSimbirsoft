package com.example.androidpractice.data.search

import com.example.androidpractice.domain.events.api.EventsApi
import com.example.androidpractice.domain.search.model.SearchResult
import com.example.androidpractice.domain.search.repo.SearchRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepoImpl @Inject constructor(
    private val eventsApi: EventsApi
) : SearchRepo {

    override suspend fun searchEventsByOrganizationName(query: String): SearchResult {
        return eventsApi.searchByOrganization(query).blockingFirst()
    }

    override suspend fun searchEventsByName(query: String): SearchResult {
        return eventsApi.searchByEvent(query).blockingFirst()
    }
}