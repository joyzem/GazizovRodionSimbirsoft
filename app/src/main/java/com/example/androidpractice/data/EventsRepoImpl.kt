package com.example.androidpractice.data

import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsApi
import com.example.androidpractice.domain.repo.EventsRepo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepoImpl @Inject constructor(
    private val eventsApi: EventsApi
) : EventsRepo {

    private val _readEvents: Subject<String> = PublishSubject.create()
    override val readEvents: Observable<String> = _readEvents

    private val _events = MutableStateFlow<List<Event>?>(null)
    override val events: StateFlow<List<Event>?> = _events

    override fun updateCachedEvents(events: List<Event>) {
        _events.update {
            events
        }
    }

    override fun readEvent(eventId: String) {
        _readEvents.onNext(eventId)
    }

    override fun searchEventsByOrganization(query: String): Observable<SearchResult> {
        return eventsApi.searchByOrganization(query)
    }

    override fun searchEventsByName(query: String): Observable<SearchResult> {
        return eventsApi.searchByEvent(query)
    }
}
