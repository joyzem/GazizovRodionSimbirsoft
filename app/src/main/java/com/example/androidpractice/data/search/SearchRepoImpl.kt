package com.example.androidpractice.data.search

import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.domain.search.model.SearchResult
import com.example.androidpractice.domain.search.repo.SearchRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepoImpl @Inject constructor(
    private val eventsRepo: EventsRepo
) : SearchRepo {

    override suspend fun searchEventsByOrganizationName(query: String): SearchResult {
        return eventsRepo.searchEventByOrganizationName(query).blockingFirst()
    }

    override suspend fun searchEventsByName(query: String): SearchResult {
        return eventsRepo.searchEventByName(query).blockingFirst()
    }
}