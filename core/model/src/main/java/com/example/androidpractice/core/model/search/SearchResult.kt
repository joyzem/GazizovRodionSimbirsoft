package com.example.androidpractice.core.model.search

import com.example.androidpractice.core.model.event.Event

data class SearchResult(
    val id: String,
    val keywords: List<String>,
    val events: List<Event>
)
