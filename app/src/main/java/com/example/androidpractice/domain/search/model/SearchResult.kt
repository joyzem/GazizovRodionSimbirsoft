package com.example.androidpractice.domain.search.model

import com.example.androidpractice.domain.events.model.Event

data class SearchResult(
    val id: String,
    val keywords: List<String>,
    val events: List<Event>
)
