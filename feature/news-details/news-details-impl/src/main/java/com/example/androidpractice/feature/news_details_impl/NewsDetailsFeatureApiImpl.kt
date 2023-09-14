package com.example.androidpractice.feature.news_details_impl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import javax.inject.Inject

class NewsDetailsFeatureApiImpl @Inject constructor() : NewsDetailsFeatureApi {

    override fun newsDetailsFragment(newsId: String) = NewsDetailsFragment.newInstance(newsId)
    override fun createNotificationChannel(context: Context): NotificationChannel? {
        val channelDescription = context.getString(R.string.donation_notification_id)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(notificationChannelId.toString(), channelDescription, importance)
        } else {
            null
        }
        return channel
    }

    companion object {
        val notificationChannelId = R.string.donation_notification_id
    }
}
