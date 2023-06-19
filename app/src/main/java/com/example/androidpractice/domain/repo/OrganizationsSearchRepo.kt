package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.SearchResult

interface OrganizationsSearchRepo {

    fun search(query: String): List<SearchResult>

    companion object {
        val mockResults: List<SearchResult>
            get() = listOf(
                SearchResult("1", "Благотворительный фонд Алины Кабаевой"),
                SearchResult("2", "Во имя жизни"),
                SearchResult("3", "Благотворительный фонд В. Потанина"),
                SearchResult("4", "Детские домики"),
                SearchResult("5", "Мозаика счастья"),
            )
    }
}
