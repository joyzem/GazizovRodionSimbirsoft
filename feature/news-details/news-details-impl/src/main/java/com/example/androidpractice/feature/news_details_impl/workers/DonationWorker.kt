package com.example.androidpractice.feature.news_details_impl.workers

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.CalendarContract.Instances.EVENT_ID
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.PendingIntentCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi.Companion.EVENT_ID_KEY
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi.Companion.EVENT_NAME_KEY
import com.example.androidpractice.feature.news_details_impl.NewsDetailsFeatureApiImpl
import com.example.androidpractice.feature.news_details_impl.NewsDetailsFragment
import com.example.androidpractice.feature.news_details_impl.R
import com.example.androidpractice.feature.news_details_impl.broadcast.RemindLaterBroadcast
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

internal class DonationWorker(
    private val activityContext: Context,
    params: WorkerParameters
) : Worker(activityContext.applicationContext, params) {

    override fun doWork(): Result {
        val eventName = inputData.getString(EVENT_NAME_KEY) ?: throw IllegalStateException()
        val eventId = inputData.getString(EVENT_ID_KEY) ?: throw IllegalStateException()
        val donationAmount = inputData.getInt(DONATION_AMOUNT, 0)
        check(donationAmount != 0)

        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).apply {
            currency = Currency.getInstance("RUB")
        }

        val remindLaterIntent = PendingIntentCompat.getBroadcast(
            activityContext,
            0,
            Intent(activityContext, RemindLaterBroadcast::class.java).apply {
                putExtra(EVENT_NAME_KEY, eventName)
                putExtra(EVENT_ID, eventId)
            },
            PendingIntent.FLAG_ONE_SHOT,
            false
        )

        val openEventScreenIntent =
            NewsDetailsFragment.getScreenDeepLinkIntent(applicationContext, eventId)

        val notificationId = NewsDetailsFeatureApiImpl.notificationChannelId
        val notification =
            NotificationCompat.Builder(activityContext, notificationId.toString())
                .setContentTitle(eventName)
                .setSmallIcon(com.example.androidpractice.core.designsystem.R.drawable.ic_icon_logo)
                .setContentIntent(openEventScreenIntent)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            activityContext.getString(
                                R.string.thanks_for_donation_of,
                                currencyFormatter.format(donationAmount)
                            )
                        )
                )
                .addAction(
                    NotificationCompat.Action(
                        null,
                        activityContext.getString(R.string.remind_later),
                        remindLaterIntent
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

    companion object {
        const val DONATION_AMOUNT = "donation_amount"

        fun createInputData(
            eventId: String,
            eventName: String,
            donationAmount: Int
        ) = workDataOf(
            EVENT_ID_KEY to eventId,
            EVENT_NAME_KEY to eventName,
            DONATION_AMOUNT to donationAmount
        )
    }
}
