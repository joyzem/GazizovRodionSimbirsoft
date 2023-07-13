package com.example.androidpractice.domain.model

data class SearchResult(
    val id: String,
    val keywords: List<String>,
    val events: List<Event>
)
