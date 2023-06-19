package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.SearchResult

interface EventsSearchRepo {

    fun search(query: String): List<SearchResult>

    companion object {
        val mockResults = listOf(
            SearchResult("1", "Благотворительный фонд Алины Кабаевой"),
            SearchResult("2", "Во имя жизни"),
            SearchResult("3", "Благотворительный фонд В. Потанина"),
            SearchResult("4", "Детские домики"),
            SearchResult("5", "Мозаика счастья"),
        )
    }
}
