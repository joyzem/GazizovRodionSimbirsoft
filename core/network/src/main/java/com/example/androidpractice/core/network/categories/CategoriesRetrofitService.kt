package com.example.androidpractice.core.network.categories

import retrofit2.http.GET

interface CategoriesRetrofitService {

    @GET("categories")
    suspend fun fetchCategories(): List<CategoryDTO>
}
