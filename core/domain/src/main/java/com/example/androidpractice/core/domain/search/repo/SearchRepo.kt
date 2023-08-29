package com.example.androidpractice.core.domain.search.repo

import com.example.androidpractice.core.model.search.SearchResult

interface SearchRepo {

    suspend fun searchEventsByOrganizationName(query: String): SearchResult

    suspend fun searchEventsByName(query: String): SearchResult
}
