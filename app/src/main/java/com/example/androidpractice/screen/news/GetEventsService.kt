package com.example.androidpractice.screen.news

import android.app.IntentService
import android.content.Intent
import android.content.res.AssetManager
import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.ui.getAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetEventsService : IntentService("GetEventsService") {

    @Inject
    lateinit var repo: EventsRepo

    @Inject
    lateinit var assetManager: AssetManager

    private val context = Job() + Dispatchers.IO

    override fun onCreate() {
        super.onCreate()
        getAppComponent().inject(this)
    }

    override fun onHandleIntent(p0: Intent?) {
        try {
            CoroutineScope(context).launch {
                repo.fetchEvents()
                cancel()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            stopSelf()
        }
    }
}
