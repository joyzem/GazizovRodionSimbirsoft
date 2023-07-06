package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.Event
import kotlinx.coroutines.flow.StateFlow

interface EventsRepo {
    val events: StateFlow<List<Event>?>

    fun updateCachedEvents(events: List<Event>)
}
