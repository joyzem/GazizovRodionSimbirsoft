package com.example.androidpractice.data.events.dto

import com.example.androidpractice.domain.events.model.Event
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class EventDTO(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val description: String,
    val status: Long,
    val photos: List<String>,
    @SerializedName("category")
    val categoryId: String,
    val createAt: Long,
    val phone: String,
    val address: String,
    val organisation: String
)

fun EventDTO.toModel() = Event(
    id = id,
    title = name,
    subtitle = description,
    description = description,
    sponsor = organisation,
    startDate = Instant.fromEpochMilliseconds(startDate)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    endDate = Instant.fromEpochMilliseconds(endDate)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    createAt = Instant.fromEpochMilliseconds(createAt)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    address = address,
    phoneNumber = phone,
    email = "example@gmail.com",
    siteUrl = "google.com",
    imagePreview = photos.firstOrNull() ?: "",
    imageUrls = photos,
    category = categoryId,
)
