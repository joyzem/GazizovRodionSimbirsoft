package com.example.androidpractice.data.events

import android.annotation.SuppressLint
import android.util.Log
import com.example.androidpractice.data.events.dto.toModel
import com.example.androidpractice.data.events.network.EventsRetrofitApi
import com.example.androidpractice.domain.events.api.EventsApi
import com.example.androidpractice.domain.events.model.Event
import com.example.androidpractice.domain.search.model.SearchResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.UUID
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsApiImpl @Inject constructor(
    private val api: EventsRetrofitApi
) : EventsApi {

    /**
     * 7 пункт из практического задания
     * Используется в [searchByOrganization]
     */
    private val organizationsQueries = BehaviorSubject.create<String>()
    private val events = BehaviorSubject.create<List<Event>>()

    override fun searchByEvent(query: String): Observable<SearchResult> {
        return fetchEvents().map { events ->
            events.filter {
                it.title.contains(query)
            }
        }.map { events ->
            SearchResult(
                id = UUID.randomUUID().toString(),
                keywords = events.map {
                    it.title
                },
                events = events
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun searchByOrganization(query: String): Observable<SearchResult> {

        /**
        Изучив логи, можно сделать следующие выводы:
        - при комбинации двух Observable поток, на котором выполняется лямбда
        соответствует последнему по времени выполнения из двух комбинируемых потоков,
        - возможен 1 subscribeOn и множенство observeOn
        - наиболее подходящий вариант использован в [searchByEvent]
        - применять subscribeOn на [organizationEvents] Observable бессмысленно. Видимо,
        это связано с тем, что ранее subscribeOn был вызван на обоих "потоках"
        - по ощущениям, subscribeOn вообще не меняет поток
         */

        val loggedEvents = events.loggingTest("events")
        val loggedQueries = organizationsQueries.loggingTest("organizations")
        val organizationEvents = Observable.combineLatest(
            loggedEvents.observeOn(Schedulers.computation()),
            loggedQueries.observeOn(Schedulers.from(Executors.newSingleThreadExecutor())),
        ) { events, organizationQuery ->
            Log.i(TAG, "Combine latest default thread: ${Thread.currentThread().name}")
            events.filter { event ->
                event.sponsor.contains(organizationQuery, true)
            }
        }
            .doOnNext {
                Log.i(TAG, "Organization events default thread: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Log.i(
                    TAG,
                    "Organization events subscribe on io thread: ${Thread.currentThread().name}"
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.i(
                    TAG,
                    "Organization events observe on main thread: ${Thread.currentThread().name}"
                )
            }
        organizationsQueries.onNext(query)
        events.onNext(fetchEvents().blockingFirst())
        return organizationEvents
            .delay(1000, TimeUnit.MILLISECONDS)
            .map { events ->
                SearchResult(
                    id = UUID.randomUUID().toString(),
                    keywords = events.map {
                        it.title
                    },
                    events = events
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchEvents(): Observable<List<Event>> {
        return api.fetchEvents().map { events ->
            events.map {
                it.toModel()
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun <T : Any> Observable<T>.loggingTest(observableName: String): Observable<T> {
        return this.logObserveOnThreadNameOnNext(observableName, "default")
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Log.i(
                    TAG,
                    "$observableName: subscribe on io thread – ${Thread.currentThread().name}"
                )
            }
            .observeOn(Schedulers.computation())
            .logObserveOnThreadNameOnNext(observableName, "computation")
            .observeOn(Schedulers.newThread())
            .logObserveOnThreadNameOnNext(observableName, "new thread")
            .observeOn(Schedulers.single())
            .logObserveOnThreadNameOnNext(observableName, "single")
            .observeOn(Schedulers.trampoline())
            .logObserveOnThreadNameOnNext(observableName, "trampoline")
            .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
            .logObserveOnThreadNameOnNext(observableName, "executors single thread")
            .subscribeOn(Schedulers.computation())
            .doOnNext {
                Log.i(
                    TAG,
                    "$observableName: subscribe on computation thread but should be previous– ${Thread.currentThread().name}"
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .logObserveOnThreadNameOnNext(observableName, "main")
    }

    private fun <T : Any> Observable<T>.logObserveOnThreadNameOnNext(
        observableName: String,
        threadName: String
    ): Observable<T> {
        return this.doOnNext {
            Log.i(TAG, "$observableName: observe on $threadName – ${Thread.currentThread().name}")
        }
    }

    companion object {
        const val TAG = "EventsApiImpl"
    }
}
