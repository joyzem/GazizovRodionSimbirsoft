package com.example.androidpractice.domain.categories.api

import com.example.androidpractice.domain.categories.model.Category
import io.reactivex.rxjava3.core.Observable

interface CategoriesApi {

    fun fetchCategories(): Observable<List<Category>>
}