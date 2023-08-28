package com.example.androidpractice.screen.news.details

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.core.ui.BaseViewModel
import com.example.utils.concurrent.getLoggingCoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EventDetailsViewModel"

class EventDetailsViewModel @Inject constructor(
    private val eventsRepo: EventsRepo
) : BaseViewModel() {
    val events = eventsRepo.events.asLiveData(viewModelScope.coroutineContext)

    fun readEvent(eventId: String) {
        val ceh = getLoggingCoroutineExceptionHandler(TAG)
        viewModelScope.launch(Dispatchers.IO + ceh) {
            eventsRepo.readEvent(eventId)
        }
    }
}
