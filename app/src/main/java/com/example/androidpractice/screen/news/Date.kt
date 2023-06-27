package com.example.androidpractice.screen.news

import android.view.View
import com.example.androidpractice.R
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getEventDateText(view: View, dates: List<LocalDate>): String {
    val dateText = if (dates.size == 1) {
        val localDate = dates.first().toJavaLocalDate()
        val formatter = SimpleDateFormat("LLL d, y", Locale("ru"))
        val formattedDate = formatter.format(java.sql.Date.valueOf(localDate.toString()))
            .replaceFirstChar { it.uppercase() }
        view.resources.getString(
            R.string.event_certain_day,
            formattedDate
        )
    } else {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM")

        val today =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        val lastDay = dates.last()
        val lastDayFormatted = lastDay.toJavaLocalDate().format(dateFormatter)

        val firstDay = dates.first()
        val firstDayFormatted = firstDay.toJavaLocalDate().format(dateFormatter)

        val period = lastDay.minus(today).days

        if (period >= 0) {
            view.resources.getQuantityString(
                R.plurals.event_date,
                period,
                period.toString(),
                firstDayFormatted,
                lastDayFormatted
            )
        } else {
            view.resources.getString(R.string.event_is_over, lastDayFormatted, firstDayFormatted)
        }
    }
    return dateText
}