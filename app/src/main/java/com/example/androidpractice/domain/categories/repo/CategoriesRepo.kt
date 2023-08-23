package com.example.androidpractice.domain.categories.repo

import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.model.CategoryFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoriesRepo {

    val appliedFilters: StateFlow<List<CategoryFilter>?>

    fun fetchCategories(): Flow<List<Category>>

    fun setFilters(filters: List<CategoryFilter>)
}
