package com.example.androidpractice.domain.model

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDate

data class Event(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val date: List<LocalDate>,
    val address: String,
    @SerializedName("phone_numbers")
    val phoneNumbers: List<String>,
    val email: String,
    @SerializedName("site_url")
    val siteUrl: String,
    @Transient
    @DrawableRes
    val imagePreviewId: Int,                // TODO: should be link
    @Transient
    @DrawableRes
    val imageIds: List<Int>,                // TODO: should be list of links
    val categories: List<Category>,
    val people: List<User>
)
