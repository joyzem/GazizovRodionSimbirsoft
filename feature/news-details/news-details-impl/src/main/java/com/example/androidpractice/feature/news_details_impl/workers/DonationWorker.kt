package com.example.androidpractice.feature.news_details_impl.workers

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.PendingIntentCompat
import androidx.core.os.bundleOf
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.androidpractice.feature.news_details_impl.NewsDetailsFeatureApiImpl
import com.example.androidpractice.feature.news_details_impl.R
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class DonationWorker(
    private val activityContext: Context,
    private val params: WorkerParameters
) : Worker(activityContext.applicationContext, params) {

    override fun doWork(): Result {
        val eventName = params.inputData.getString(EVENT_NAME) ?: throw IllegalStateException()
        val eventId = params.inputData.getString(EVENT_ID) ?: throw IllegalStateException()
        val donationAmount =
            params.inputData.getInt(DONATION_AMOUNT, 0)
        check(donationAmount != 0)

        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).apply {
            currency = Currency.getInstance("RUB")
        }

        val notificationId = NewsDetailsFeatureApiImpl.notificationChannelId
        val rememberLaterIntent = PendingIntentCompat.getActivity(
            activityContext,
            0,
            Intent(),
            PendingIntent.FLAG_ONE_SHOT,
            bundleOf(
                EVENT_ID to eventId,
                EVENT_NAME to eventName,
                DONATION_AMOUNT to donationAmount
            ),
            false
        )
        val openEventScreenIntent = PendingIntentCompat.getActivity(
            activityContext,
            0,
            Intent(),
            PendingIntent.FLAG_ONE_SHOT,
            bundleOf(
                EVENT_ID to eventId
            ),
            false
        )
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
                        rememberLaterIntent
                    )
                )
                .build()
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.success()
        }
        NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)

        return Result.success()
    }

    companion object {
        const val EVENT_ID = "event_id"
        const val EVENT_NAME = "event_name"
        const val DONATION_AMOUNT = "donation_amount"

        fun createInputData(
            eventId: String,
            eventName: String,
            donationAmount: Int,
        ) = workDataOf(
            EVENT_ID to eventId,
            EVENT_NAME to eventName,
            DONATION_AMOUNT to donationAmount,
        )
    }
}