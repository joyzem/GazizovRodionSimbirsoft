package com.example.androidpractice.screen.news

import android.app.IntentService
import android.content.Intent
import android.content.res.AssetManager
import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.ui.getAppComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetEventsService : IntentService("GetEventsService") {

    @Inject
    lateinit var repo: EventsRepo

    @Inject
    lateinit var assetManager: AssetManager

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        getAppComponent().inject(this)
    }

    override fun onHandleIntent(p0: Intent?) {
        try {
            val disp = repo.fetchEvents().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { events ->
                        repo.setEvents(events)
                        compositeDisposable.clear()
                    },
                    {
                        compositeDisposable.clear()
                    },
                    {
                        compositeDisposable.clear()
                    }
                )
            compositeDisposable.add(disp)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            stopSelf()
        }
    }
}
