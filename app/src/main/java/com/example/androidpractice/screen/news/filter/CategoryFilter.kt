package com.example.androidpractice.screen.news.filter

import com.example.androidpractice.domain.model.Category

data class CategoryFilter(
    val category: Category,
    val checked: Boolean
)
