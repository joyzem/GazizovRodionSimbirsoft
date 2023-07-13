package com.example.androidpractice.data

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.util.Log
import com.example.androidpractice.data.adapters.CategoryTypeAdapter
import com.example.androidpractice.domain.model.Category
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsApi
import com.example.androidpractice.util.fromJson
import com.example.androidpractice.util.getJsonFromAssets
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.UUID
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EventsApiImpl @Inject constructor(
    private val assetManager: AssetManager
) : EventsApi {

    /**
     * 7 пункт из практического задания
     * Используется в [searchByOrganization]
     */
    private val organizationsQueries = BehaviorSubject.create<String>()
    private val events = BehaviorSubject.create<List<Event>>()

    override fun searchByEvent(query: String): Observable<SearchResult> {
        return Observable.create { emitter ->
            val events = getEventsFromJson().filter { event ->
                event.title.contains(query, true)
            }
            val multiplyEvents = mutableListOf<Event>()
            repeat(100) {
                multiplyEvents += events
            }
            emitter.onNext(multiplyEvents)
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .doOnNext {
                Log.i(TAG, "Thread: ${Thread.currentThread().name}")
            }
            .delay(1000, TimeUnit.MILLISECONDS)
            .map { events ->
                SearchResult(
                    id = UUID.randomUUID().toString(),
                    keywords = events.flatMap { event ->
                        event.categories.map { category ->
                            category.title
                        }
                    }.toSet().toList(),
                    events = events
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
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
        events.onNext(getEventsFromJson())
        return organizationEvents
            .delay(1000, TimeUnit.MILLISECONDS)
            .map { events ->
                SearchResult(
                    id = UUID.randomUUID().toString(),
                    keywords = events.flatMap { event ->
                        event.categories.map { category ->
                            category.title
                        }
                    }.toSet().toList(),
                    events = events
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getEventsFromJson(): List<Event> {
        val json = getJsonFromAssets(assetManager, "events.json")
        val gson = GsonBuilder()
            .registerTypeAdapter(Category::class.java, CategoryTypeAdapter())
            .create()
        return gson.fromJson(json)
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
