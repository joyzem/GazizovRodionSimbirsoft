package com.example.androidpractice.core.database.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidpractice.core.model.event.Event
import kotlinx.datetime.LocalDate

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val sponsor: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createAt: LocalDate,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val siteUrl: String,
    val imagePreviewUrl: String,
    val imageUrls: String,
    val categoryId: String
)

fun EventEntity.toModel() = Event(
    id = id,
    title = title,
    subtitle = subtitle,
    description = description,
    sponsor = sponsor,
    startDate = startDate,
    endDate = endDate,
    createAt = createAt,
    address = address,
    phoneNumber = phoneNumber,
    email = email,
    siteUrl = siteUrl,
    imagePreview = imagePreviewUrl,
    imageUrls = imageUrls.split(";"),
    category = categoryId
)

fun Event.toEntity() = EventEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    description = description,
    sponsor = sponsor,
    startDate = startDate,
    endDate = endDate,
    createAt = createAt,
    address = address,
    phoneNumber = phoneNumber,
    email = email,
    siteUrl = siteUrl,
    imagePreviewUrl = imagePreview,
    imageUrls = imageUrls.joinToString(separator = ";"),
    categoryId = category

)
