package com.example.androidpractice.domain.model

import androidx.annotation.DrawableRes
import kotlinx.datetime.DatePeriod

data class Event(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val date: DatePeriod,
    val address: String,
    val phoneNumbers: List<String>,
    val email: String,
    val siteUrl: String,
    val imagePreviewId: Int,                // TODO: should be link
    @DrawableRes val imageIds: List<Int>,   // TODO: should be list of links
    val categories: List<Category>,
    val people: List<User>
)
