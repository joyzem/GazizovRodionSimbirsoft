package com.example.androidpractice.screen.news.details

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.ui.BaseViewModel
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor(
    private val eventsRepo: EventsRepo
) : BaseViewModel() {
    private val _events = eventsRepo.events
    val events = _events.asLiveData(viewModelScope.coroutineContext)

    fun readEvent(eventId: String) {
        eventsRepo.readEvent(eventId)
    }
}
