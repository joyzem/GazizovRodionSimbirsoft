package com.example.androidpractice.data

import android.content.res.AssetManager
import com.example.androidpractice.data.adapters.CategoryTypeAdapter
import com.example.androidpractice.domain.model.Category
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.util.fromJson
import com.example.androidpractice.util.getJsonFromAssets
import com.google.gson.GsonBuilder
import javax.inject.Inject

class EventsRepoImpl @Inject constructor(
    private val assetManager: AssetManager
) : EventsRepo {
    override fun getEvents(): List<Event> {
        val json = getJsonFromAssets(assetManager, "events.json")
        val gson = GsonBuilder()
            .registerTypeAdapter(Category::class.java, CategoryTypeAdapter())
            .create()
        return gson.fromJson(json)
    }
}