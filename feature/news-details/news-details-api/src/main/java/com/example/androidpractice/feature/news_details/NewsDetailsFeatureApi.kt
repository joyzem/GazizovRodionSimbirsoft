package com.example.androidpractice.feature.news_details

import android.app.NotificationChannel
import android.content.Context
import com.example.androidpractice.core.ui.BaseFragment

interface NewsDetailsFeatureApi {
    fun newsDetailsFragment(newsId: String): BaseFragment<*, *>
    fun createNotificationChannel(context: Context): NotificationChannel?
}
