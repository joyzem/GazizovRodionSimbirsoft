package com.example.androidpractice.data

import android.content.res.AssetManager
import com.example.androidpractice.data.adapters.CategoryTypeAdapter
import com.example.androidpractice.domain.model.Category
import com.example.androidpractice.domain.model.CategoryFilter
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.util.fromJson
import com.example.androidpractice.util.getJsonFromAssets
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepoImpl @Inject constructor(
    private val assetManager: AssetManager
) : CategoriesRepo {

    private val _appliedFilters = MutableStateFlow(
        getCategories().map {
            CategoryFilter(it, true)
        }
    )
    override val appliedFilters = _appliedFilters.asStateFlow()

    override fun getCategories(): List<Category> {
        val json = getJsonFromAssets(assetManager, "categories.json")
        val gson = GsonBuilder()
            .registerTypeAdapter(Category::class.java, CategoryTypeAdapter())
            .create()
        return gson.fromJson(json)
    }

    override fun setFilters(filters: List<CategoryFilter>) {
        _appliedFilters.update {
            filters
        }
    }
}
