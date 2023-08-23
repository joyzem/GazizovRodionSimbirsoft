package com.example.androidpractice.data.categories.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidpractice.domain.categories.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String
)

fun Category.toEntity() = CategoryEntity(
    id,
    title,
    imageUrl
)

fun CategoryEntity.toModel() = Category(
    id,
    title,
    imageUrl
)