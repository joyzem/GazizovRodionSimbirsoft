package com.example.androidpractice.data.categories

import android.content.res.AssetManager
import com.example.androidpractice.data.categories.dto.CategoryDTO
import com.example.androidpractice.data.categories.dto.toModel
import com.example.androidpractice.data.categories.network.CategoriesRetrofitApi
import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.model.CategoryFilter
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.util.json.fromJson
import com.example.androidpractice.util.json.getJsonFromAssets
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepoImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val categoriesApi: CategoriesRetrofitApi
) : CategoriesRepo {

    private val _appliedFilters: MutableStateFlow<List<CategoryFilter>?> =
        MutableStateFlow(null)
    override val appliedFilters = _appliedFilters.asStateFlow()

    override fun fetchCategories(): Flow<List<Category>> {
        return flow {
            val categories = categoriesApi.fetchCategories()
                .map { category ->
                    category.toModel()
                }
            emit(categories)
        }.onEach { newCategories ->
            val filters = newCategories.map {
                CategoryFilter(
                    it,
                    true
                )
            }
            setFilters(filters)
        }.catch {
            val categories = getCategoriesFromFile()
            val filters = categories.map {
                CategoryFilter(
                    it,
                    true
                )
            }
            setFilters(filters)
            emit(categories)
        }
    }


    override fun setFilters(filters: List<CategoryFilter>) {
        _appliedFilters.update { filters }
    }

    private fun getCategoriesFromFile(): List<Category> {
        val json = getJsonFromAssets(assetManager, "categories.json")
        val gson = GsonBuilder().create()
        val parsedCategories: List<CategoryDTO> = gson.fromJson(json)
        val categories = parsedCategories.map {
            it.toModel()
        }
        return categories
    }
}
