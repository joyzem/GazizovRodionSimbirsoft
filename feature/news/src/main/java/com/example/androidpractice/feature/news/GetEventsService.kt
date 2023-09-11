package com.example.androidpractice.feature.news

import android.app.IntentService
import android.content.Intent
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.feature.news.di.DaggerNewsComponent
import com.example.androidpractice.feature.news.di.NewsDepsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class GetEventsService : IntentService("GetEventsService") {

    @Inject
    lateinit var repo: EventsRepo

    private val context = Job() + Dispatchers.IO

    override fun onCreate() {
        super.onCreate()
        DaggerNewsComponent.builder().deps(NewsDepsProvider.deps).create().inject(this)
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
