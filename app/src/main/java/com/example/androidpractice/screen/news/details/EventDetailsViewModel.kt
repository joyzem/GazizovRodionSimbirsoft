package com.example.androidpractice.screen.news.details

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.ui.BaseViewModel
import com.example.androidpractice.util.concurrent.getLoggingCoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EventDetailsViewModel"

class EventDetailsViewModel @Inject constructor(
    private val eventsRepo: EventsRepo
) : BaseViewModel() {
    private val _events = eventsRepo.events
    val events = _events.asLiveData(viewModelScope.coroutineContext)

    fun readEvent(eventId: String) {
        val ceh = getLoggingCoroutineExceptionHandler(TAG)
        viewModelScope.launch(Dispatchers.IO + ceh) {
            eventsRepo.readEvent(eventId)
        }
    }
}
