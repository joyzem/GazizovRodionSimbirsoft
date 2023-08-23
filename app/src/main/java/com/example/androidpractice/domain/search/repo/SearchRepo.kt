package com.example.androidpractice.domain.search.repo

import com.example.androidpractice.domain.search.model.SearchResult

interface SearchRepo {

    suspend fun searchEventsByOrganizationName(query: String): SearchResult

    suspend fun searchEventsByName(query: String): SearchResult
}