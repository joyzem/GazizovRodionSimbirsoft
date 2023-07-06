package com.example.androidpractice.screen.news

import android.app.IntentService
import android.content.Intent
import android.content.res.AssetManager
import com.example.androidpractice.data.adapters.CategoryTypeAdapter
import com.example.androidpractice.domain.model.Category
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.util.fromJson
import com.example.androidpractice.util.getJsonFromAssets
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
                .registerTypeAdapter(Category::class.java, CategoryTypeAdapter())
                .create()
            repo.cacheEvents(gson.fromJson(json))
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            stopSelf()
        }
    }
}
