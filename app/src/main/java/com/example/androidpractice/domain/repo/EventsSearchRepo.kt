package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.SearchResult

interface EventsSearchRepo {

    suspend fun search(query: String): List<SearchResult>
}
