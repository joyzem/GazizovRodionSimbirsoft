package com.example.androidpractice.domain.model

import androidx.annotation.DrawableRes

data class User(
    val id: String,
    val name: String,
    @Transient
    @DrawableRes
    val imageId: Int
)
