package com.example.androidpractice.data.adapters

import com.example.androidpractice.R
import com.example.androidpractice.domain.model.Category
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class CategoryTypeAdapter : JsonSerializer<Category>, JsonDeserializer<Category> {
    override fun serialize(
        src: Category,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("id", src.id)
        jsonObject.addProperty("title", src.title)
        return jsonObject
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Category {
        val jsonObject = json.asJsonObject
        val id = jsonObject["id"].asString
        val title = jsonObject["title"].asString
        val imageId = getImageIdByTitle(title)
        return Category(id, title, imageId)
    }

    private fun getImageIdByTitle(title: String): Int {
        return when (title) {
            "Дети" -> R.drawable.ic_children_category
            "Взрослые" -> R.drawable.ic_adult_category
            "Пожилые" -> R.drawable.ic_old_category
            "Животные" -> R.drawable.ic_animals_category
            "События" -> R.drawable.ic_events_category
            else -> 0
        }
    }
}
