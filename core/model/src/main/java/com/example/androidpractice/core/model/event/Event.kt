package com.example.androidpractice.core.model.event

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDate

data class Event(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val sponsor: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createAt: LocalDate,
    val address: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val email: String,
    @SerializedName("site_url")
    val siteUrl: String,
    val imagePreview: String,
    val imageUrls: List<String>,
    val category: String
)
