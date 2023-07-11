package com.example.androidpractice.data

import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.OrganizationsSearchRepo
import com.example.androidpractice.domain.repo.OrganizationsSearchRepo.Companion.mockResults
import javax.inject.Inject
import kotlin.random.Random

class OrganizationsSearchRepoImpl @Inject constructor() : OrganizationsSearchRepo {
    override fun search(query: String): List<SearchResult> {
        return mockResults.shuffled().subList(0, Random.nextInt(1, mockResults.size))
    }
}
