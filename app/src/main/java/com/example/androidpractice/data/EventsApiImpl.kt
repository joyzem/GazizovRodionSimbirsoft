package com.example.androidpractice.data

import android.content.res.AssetManager
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
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EventsApiImpl @Inject constructor(
    private val assetManager: AssetManager
) : EventsApi {

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
        return Observable.create { emitter ->
            val events = getEventsFromJson().filter { event ->
                event.sponsor.contains(query, true)
            }
            emitter.onNext(events)
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
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
}
