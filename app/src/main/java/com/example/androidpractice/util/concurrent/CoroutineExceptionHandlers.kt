package com.example.androidpractice.util.concurrent

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

fun getLoggingCoroutineExceptionHandler(tag: String): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        Log.e(tag, "error: ${throwable.localizedMessage}")
    }
}