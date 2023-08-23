package com.example.androidpractice.data.local.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun fromString(isoString: String): LocalDate {
        return LocalDate.parse(isoString)
    }
}
