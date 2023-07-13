package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.SearchResult

interface OrganizationsSearchRepo {

    suspend fun search(query: String): List<SearchResult>
}
