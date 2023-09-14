package com.example.androidpractice.feature.news_details_impl.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi.Companion.EVENT_ID_KEY
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi.Companion.EVENT_NAME_KEY
import com.example.androidpractice.feature.news_details_impl.workers.ReminderWorker
import java.util.concurrent.TimeUnit

internal class RemindLaterBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val remindLaterWorker = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(30, TimeUnit.MINUTES)
            .setInputData(
                workDataOf(
                    EVENT_ID_KEY to checkNotNull(intent.getStringExtra(EVENT_ID_KEY)),
                    EVENT_NAME_KEY to checkNotNull(intent.getStringExtra(EVENT_NAME_KEY))
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(remindLaterWorker)
    }
}
