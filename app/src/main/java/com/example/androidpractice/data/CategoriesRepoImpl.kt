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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepoImpl @Inject constructor(
    private val assetManager: AssetManager
) : CategoriesRepo {

    private val _categories = MutableStateFlow<List<Category>?>(null)
    override val categories: StateFlow<List<Category>?> = _categories

    private val _appliedFilters: MutableStateFlow<List<CategoryFilter>?> =
        MutableStateFlow(null)
    override val appliedFilters: StateFlow<List<CategoryFilter>?> = _appliedFilters

    override fun updateCategories() {
        val executor = Executors.newFixedThreadPool(1)
        val updateCategoriesTask: Callable<Unit> = Callable {
            try {
                Thread.sleep(5000)

                val json = getJsonFromAssets(assetManager, "categories.json")
                val gson = GsonBuilder()
                    .registerTypeAdapter(Category::class.java, CategoryTypeAdapter())
                    .create()
                val parsedCategories: List<Category> = gson.fromJson(json)

                _categories.update { parsedCategories }

                _appliedFilters.update { filters ->
                    if (filters.isNullOrEmpty()) {
                        parsedCategories.map { category ->
                            CategoryFilter(category, true)
                        }
                    } else {
                        parsedCategories.map { category ->
                            filters.find { filter ->
                                filter.category.id == category.id
                            } ?: CategoryFilter(category, true)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                executor.shutdown()
            }
        }
        executor.submit(updateCategoriesTask)
    }

    override fun setFilters(filters: List<CategoryFilter>) {
        _appliedFilters.value = filters
    }
}
