package com.example.androidpractice.screen.news.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.repo.EventsRepo
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor(
    private val eventsRepo: EventsRepo
) : ViewModel() {
    private val _events = eventsRepo.events
    val events = _events.asLiveData(viewModelScope.coroutineContext)

    fun readEvent(eventId: String) {
        eventsRepo.readEvent(eventId)
    }
}
