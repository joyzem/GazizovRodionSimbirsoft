package com.example.androidpractice.data.categories.network

import com.example.androidpractice.data.categories.dto.CategoryDTO
import retrofit2.http.GET

interface CategoriesRetrofitApi {

    @GET("categories")
    suspend fun fetchCategories(): List<CategoryDTO>
}