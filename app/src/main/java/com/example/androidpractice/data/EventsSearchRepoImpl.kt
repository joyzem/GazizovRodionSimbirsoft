package com.example.androidpractice.data

import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsSearchRepo
import com.example.androidpractice.domain.repo.EventsSearchRepo.Companion.mockResults
import kotlin.random.Random

class EventsSearchRepoImpl : EventsSearchRepo {
    override fun search(query: String): List<SearchResult> {
        return mockResults.shuffled().subList(0, Random.nextInt(1, mockResults.size))
    }
}
