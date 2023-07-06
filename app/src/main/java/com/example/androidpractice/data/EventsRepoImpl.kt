package com.example.androidpractice.data

import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.repo.EventsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepoImpl @Inject constructor() : EventsRepo {

    private val _events = MutableStateFlow<List<Event>?>(null)
    override val events: StateFlow<List<Event>?>
        get() = _events

    override fun cacheEvents(events: List<Event>) {
        _events.update {
            events
        }
    }
}
