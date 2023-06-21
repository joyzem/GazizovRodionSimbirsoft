package com.example.androidpractice.data

import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.OrganizationsSearchRepo
import com.example.androidpractice.domain.repo.OrganizationsSearchRepo.Companion.mockResults
import kotlin.random.Random

class OrganizationsSearchRepoImpl : OrganizationsSearchRepo {
    override fun search(query: String): List<SearchResult> {
        return mockResults.shuffled().subList(0, Random.nextInt(1, mockResults.size))
    }
}
