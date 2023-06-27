package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.Category

interface CategoriesRepo {
    fun getCategories(): List<Category>
}
