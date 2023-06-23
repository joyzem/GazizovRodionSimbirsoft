package com.example.androidpractice.domain.repo

import com.example.androidpractice.domain.model.Event

interface EventsRepo {
    fun getEvents(): List<Event>
}
