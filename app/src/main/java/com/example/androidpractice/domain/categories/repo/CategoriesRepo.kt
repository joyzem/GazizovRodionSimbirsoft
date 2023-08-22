package com.example.androidpractice.domain.categories.repo

import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.model.CategoryFilter
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.StateFlow

interface CategoriesRepo {

    val appliedFilters: StateFlow<List<CategoryFilter>?>
    val categories: StateFlow<List<Category>?>

    fun fetchCategories(): Observable<List<Category>>

    fun setFilters(filters: List<CategoryFilter>)
}
