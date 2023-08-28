package com.example.androidpractice.core.data.search

import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.core.domain.search.repo.SearchRepo
import com.example.androidpractice.core.model.search.SearchResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchRepoImpl @Inject constructor(
    private val eventsRepo: EventsRepo
) : SearchRepo {

    override suspend fun searchEventsByOrganizationName(query: String): SearchResult {
        return eventsRepo.searchEventByOrganizationName(query).first()
    }

    override suspend fun searchEventsByName(query: String): SearchResult {
        return eventsRepo.searchEventByName(query).first()
    }
}
