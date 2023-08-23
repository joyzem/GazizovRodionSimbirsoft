package com.example.androidpractice.data.events.network

import com.example.androidpractice.data.events.dto.EventDTO
import retrofit2.http.GET

interface EventsRetrofitApi {

    @GET("events")
    suspend fun fetchEvents(): List<EventDTO>
}