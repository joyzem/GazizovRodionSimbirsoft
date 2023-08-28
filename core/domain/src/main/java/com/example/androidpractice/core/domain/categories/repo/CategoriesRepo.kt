package com.example.androidpractice.core.domain.categories.repo

import com.example.androidpractice.core.model.category.Category
import com.example.androidpractice.core.model.category.CategoryFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoriesRepo {

    val appliedFilters: StateFlow<List<CategoryFilter>?>
    val categories: Flow<List<Category>>

    suspend fun fetchCategories()

    fun setFilters(filters: List<CategoryFilter>)
}
