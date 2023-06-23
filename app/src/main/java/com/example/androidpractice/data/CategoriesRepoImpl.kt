package com.example.androidpractice.data

import android.content.res.AssetManager
import com.example.androidpractice.R
import com.example.androidpractice.domain.model.Category
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.util.fromJson
import com.google.gson.Gson
import java.io.BufferedInputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepoImpl @Inject constructor(
    private val assetManager: AssetManager
) : CategoriesRepo {
    override fun getCategories(): List<Category> {
        val gson = Gson()
        val categories: List<Category> = gson.fromJson(getJSON())
        return categories.map { category ->
            val imageId = when (category.title) {
                "Дети" -> R.drawable.ic_children_category
                "Взрослые" -> R.drawable.ic_adult_category
                "Пожилые" -> R.drawable.ic_old_category
                "Животные" -> R.drawable.ic_animals_category
                "События" -> R.drawable.ic_events_category
                else -> 0
            }
            category.copy(imageId = imageId)
        }
    }

    private fun getJSON(): String {
        val inputStream = BufferedInputStream(assetManager.open("categories.json"))
        var json: String
        inputStream.use { stream ->
            val size = stream.available()
            val content = ByteArray(size)
            stream.read(content)
            json = String(content)
        }
        return json
    }
}
