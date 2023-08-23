package com.example.androidpractice.domain.categories.repo

import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.model.CategoryFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoriesRepo {

    val appliedFilters: StateFlow<List<CategoryFilter>?>
    val categories: Flow<List<Category>>

    suspend fun fetchCategories()

    fun setFilters(filters: List<CategoryFilter>)
}
