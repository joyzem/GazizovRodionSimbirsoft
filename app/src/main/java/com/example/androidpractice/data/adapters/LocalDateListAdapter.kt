package com.example.androidpractice.data.adapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import kotlinx.datetime.LocalDate
import java.lang.reflect.Type

class LocalDateListAdapter : JsonSerializer<List<LocalDate>>, JsonDeserializer<List<LocalDate>> {

    override fun serialize(
        src: List<LocalDate>,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonArray = JsonArray()
        src.forEach { date ->
            jsonArray.add("${date.year}-${date.monthNumber}-${date.dayOfMonth}")
        }
        return jsonArray
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): List<LocalDate> {
        val jsonArray = json.asJsonArray
        val dateList = mutableListOf<LocalDate>()
        jsonArray.forEach { jsonElement ->
            val dateString = jsonElement.asString
            val date = LocalDate.parse(dateString)
            dateList.add(date)
        }
        return dateList
    }
}