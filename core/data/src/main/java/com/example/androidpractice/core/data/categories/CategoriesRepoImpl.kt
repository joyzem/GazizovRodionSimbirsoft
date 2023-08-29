package com.example.androidpractice.core.data.categories

import android.content.res.AssetManager
import com.example.androidpractice.core.database.categories.CategoriesDao
import com.example.androidpractice.core.database.categories.toEntity
import com.example.androidpractice.core.database.categories.toModel
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.model.category.Category
import com.example.androidpractice.core.model.category.CategoryFilter
import com.example.androidpractice.core.network.categories.CategoriesRetrofitService
import com.example.androidpractice.core.network.categories.CategoryDTO
import com.example.androidpractice.core.network.categories.toModel
import com.example.utils.json.fromJson
import com.example.utils.json.getJsonFromAssets
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CategoriesRepoImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val categoriesDao: CategoriesDao,
    private val categoriesApi: CategoriesRetrofitService
) : CategoriesRepo {

    private val _appliedFilters: MutableStateFlow<List<CategoryFilter>?> =
        MutableStateFlow(null)
    override val appliedFilters = _appliedFilters.asStateFlow()

    private var categoriesFetched = false
    override val categories: Flow<List<Category>> = categoriesDao.getCategories()
        .map { entities ->
            entities.map {
                it.toModel()
            }
        }
        .flowOn(Dispatchers.IO)
        .catch {
            emit(getCategoriesFromFile())
        }

    override suspend fun fetchCategories() {
        if (categoriesFetched) return
        val categories = try {
            categoriesApi.fetchCategories()
                .map { category ->
                    category.toModel()
                }
        } catch (e: Exception) {
            getCategoriesFromFile()
        }
        val filters = categories.map {
            CategoryFilter(
                it,
                true
            )
        }
        setFilters(filters)
        val entities = categories.map {
            it.toEntity()
        }
        categoriesDao.insertCategories(entities)
        categoriesFetched = true
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
