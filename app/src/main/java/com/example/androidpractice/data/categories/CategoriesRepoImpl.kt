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
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepoImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val categoriesApi: CategoriesRetrofitApi
) : CategoriesRepo {

    private val _categories = MutableStateFlow<List<Category>?>(null)
    override val categories = _categories.asStateFlow()

    private val _appliedFilters: MutableStateFlow<List<CategoryFilter>?> =
        MutableStateFlow(null)
    override val appliedFilters = _appliedFilters.asStateFlow()

    override fun fetchCategories(): Observable<List<Category>> {
        return categoriesApi.fetchCategories()
            .map { categories ->
                categories.map { dto ->
                    dto.toModel()
                }
            }
            .doOnNext { newCategories ->
                _categories.update {
                    newCategories
                }
                setFilters(
                    newCategories.map {
                        CategoryFilter(
                            it,
                            true
                        )
                    }
                )
            }.onErrorReturn {
                val categories = getCategoriesFromFile()
                _categories.update {
                    categories
                }
                setFilters(
                    categories.map {
                        CategoryFilter(
                            it,
                            true
                        )
                    }
                )
                categories
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
