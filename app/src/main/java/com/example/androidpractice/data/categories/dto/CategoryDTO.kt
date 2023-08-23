package com.example.androidpractice.data.categories.dto

import com.example.androidpractice.domain.categories.model.Category
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