package com.example.androidpractice.feature.news_details_impl.workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import com.example.androidpractice.feature.news_details_impl.NewsDetailsFeatureApiImpl
import com.example.androidpractice.feature.news_details_impl.NewsDetailsFragment
import com.example.androidpractice.feature.news_details_impl.R

internal class ReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val eventName = inputData.getString(NewsDetailsFeatureApi.EVENT_NAME_KEY) ?: throw IllegalStateException()
        val eventId = inputData.getString(NewsDetailsFeatureApi.EVENT_ID_KEY) ?: throw IllegalStateException()

        val notificationId = NewsDetailsFeatureApiImpl.notificationChannelId

        val openEventScreenIntent =
            NewsDetailsFragment.getScreenDeepLinkIntent(applicationContext, eventId)

        val notification =
            NotificationCompat.Builder(applicationContext, notificationId.toString())
                .setContentTitle(eventName)
                .setSmallIcon(com.example.androidpractice.core.designsystem.R.drawable.ic_icon_logo)
                .setContentIntent(openEventScreenIntent)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            applicationContext.getString(
                                R.string.remind_you_we_will_be_pleasure
                            )
                        )
                )
                .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }
        NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)

        return Result.success()
    }
}
