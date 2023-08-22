package com.example.androidpractice.data.categories

import android.content.res.AssetManager
import com.example.androidpractice.data.categories.dto.CategoryDTO
import com.example.androidpractice.data.categories.dto.toModel
import com.example.androidpractice.domain.categories.api.CategoriesApi
import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.model.CategoryFilter
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.util.json.fromJson
import com.example.androidpractice.util.json.getJsonFromAssets
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepoImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val categoriesApi: CategoriesApi
) : CategoriesRepo {

    private val _categories = MutableStateFlow<List<Category>?>(null)
    override val categories: StateFlow<List<Category>?> = _categories

    private val _appliedFilters: MutableStateFlow<List<CategoryFilter>?> =
        MutableStateFlow(null)
    override val appliedFilters: StateFlow<List<CategoryFilter>?> = _appliedFilters

    override fun fetchCategories(): Observable<List<Category>> {
        return categoriesApi.fetchCategories()
            .doOnNext {
                _categories.update {
                    it
                }
            }.onErrorReturn {
                val json = getJsonFromAssets(assetManager, "categories.json")
                val gson = GsonBuilder()
                    .create()
                val parsedCategories: List<CategoryDTO> = gson.fromJson(json)
                parsedCategories.map {
                    it.toModel()
                }
            }
    }

    override fun setFilters(filters: List<CategoryFilter>) {
        _appliedFilters.value = filters
    }
}
