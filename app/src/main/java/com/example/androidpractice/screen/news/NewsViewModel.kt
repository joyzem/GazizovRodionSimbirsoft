package com.example.androidpractice.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.androidpractice.domain.repo.EventsRepo
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val eventsRepo: EventsRepo
): ViewModel() {

    val events = liveData {
        emit(
            eventsRepo.getEvents()
        )
    }
}