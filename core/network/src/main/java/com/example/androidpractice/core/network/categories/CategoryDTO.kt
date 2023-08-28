package com.example.androidpractice.core.network.categories

import com.example.androidpractice.core.model.category.Category
import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    val id: String,
    @SerializedName("name_en")
    val nameEn: String,
    val name: String,
    @SerializedName("image")
    val imageUrl: String
)

fun CategoryDTO.toModel() = Category(
    id = id,
    title = name,
    imageUrl = imageUrl
)
