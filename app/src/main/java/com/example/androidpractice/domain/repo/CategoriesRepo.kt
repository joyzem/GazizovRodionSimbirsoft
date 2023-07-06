package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.Category
import com.example.androidpractice.domain.model.CategoryFilter
import kotlinx.coroutines.flow.StateFlow

interface CategoriesRepo {

    val appliedFilters: StateFlow<List<CategoryFilter>?>
    val categories: StateFlow<List<Category>?>

    fun updateCategories()

    fun setFilters(filters: List<CategoryFilter>)
}
