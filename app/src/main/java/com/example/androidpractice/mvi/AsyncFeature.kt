package com.example.androidpractice.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AsyncFeature<State, Wish, News> {
    val state: StateFlow<State>
    val news: SharedFlow<News> // SingleLiveEvent
    fun newWish(scope: CoroutineScope, wish: Wish)
}