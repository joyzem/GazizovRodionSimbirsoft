package com.example.androidpractice.core.network.events

import retrofit2.http.GET

interface EventsRetrofitService {

    @GET("events")
    suspend fun fetchEvents(): List<EventDTO>
}
