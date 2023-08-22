package com.example.androidpractice.domain.events.model

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
    val address: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val email: String,
    @SerializedName("site_url")
    val siteUrl: String,
    @Transient
    val imagePreview: String,
    @Transient
    val imageIds: List<String>,
    val category: String
)
