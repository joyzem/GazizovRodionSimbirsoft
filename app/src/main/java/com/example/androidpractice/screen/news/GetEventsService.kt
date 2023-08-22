package com.example.androidpractice.screen.news

import android.app.IntentService
import android.content.Intent
import android.content.res.AssetManager
import com.example.androidpractice.data.events.dto.EventDTO
import com.example.androidpractice.data.events.dto.toModel
import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.util.json.fromJson
import com.example.androidpractice.util.json.getJsonFromAssets
import com.google.gson.GsonBuilder
import javax.inject.Inject

class GetEventsService : IntentService("GetEventsService") {

    @Inject
    lateinit var repo: EventsRepo

    @Inject
    lateinit var assetManager: AssetManager

    override fun onCreate() {
        super.onCreate()
        getAppComponent().inject(this)
    }

    override fun onHandleIntent(p0: Intent?) {
        try {
            Thread.sleep(5000)
            val json = getJsonFromAssets(assetManager, "events.json")
            val gson = GsonBuilder()
                .create()
            val events = gson.fromJson<List<EventDTO>>(json).map {
                it.toModel()
            }
            repo.updateCachedEvents(events)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            stopSelf()
        }
    }
}
