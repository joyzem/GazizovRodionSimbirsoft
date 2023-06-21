package com.example.androidpractice.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    @StringRes val titleId: Int,
    @DrawableRes val imageId: Int // Should be link
)
