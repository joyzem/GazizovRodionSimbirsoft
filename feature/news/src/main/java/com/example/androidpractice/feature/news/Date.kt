package com.example.androidpractice.feature.news

import android.content.Context
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getEventDateText(context: Context, dateStart: LocalDate, dateEnd: LocalDate): String {
    val dateText = if (dateStart == dateEnd) {
        val localDate = dateStart.toJavaLocalDate()
        val formatter = SimpleDateFormat("LLL d, y", Locale("ru"))
        val formattedDate = formatter.format(java.sql.Date.valueOf(localDate.toString()))
            .replaceFirstChar { it.uppercase() }
        formattedDate
    } else {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM")

        val today =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        val lastDayFormatted = dateEnd.toJavaLocalDate().format(dateFormatter)
        val firstDayFormatted = dateStart.toJavaLocalDate().format(dateFormatter)

        val period = dateEnd.minus(today).days

        if (period >= 0) {
            context.resources.getQuantityString(
                R.plurals.event_date,
                period,
                period.toString(),
                firstDayFormatted,
                lastDayFormatted
            )
        } else {
            context.resources.getString(R.string.event_is_over, lastDayFormatted, firstDayFormatted)
        }
    }
    return dateText
}
