package com.example.androidpractice.data.categories.network

import com.example.androidpractice.data.categories.dto.CategoryDTO
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface CategoriesRetrofitApi {

    @GET("categories")
    fun fetchCategories(): Observable<List<CategoryDTO>>
}