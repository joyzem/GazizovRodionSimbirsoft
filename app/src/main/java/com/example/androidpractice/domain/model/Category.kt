package com.example.androidpractice.domain.model

import androidx.annotation.DrawableRes

data class Category(
    val id: String,
    val title: String,
    @Transient
    @DrawableRes
    val imageId: Int = 0 // Should be link
)