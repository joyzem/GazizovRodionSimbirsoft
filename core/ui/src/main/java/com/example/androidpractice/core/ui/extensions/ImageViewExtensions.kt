package com.example.androidpractice.core.ui.extensions

import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import coil.request.Disposable
import coil.request.ImageRequest

fun ImageView.loadWithoutCaching(
    url: String,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    return load(url) {
        diskCachePolicy(CachePolicy.DISABLED)
        crossfade(true)
        apply(builder)
    }
}
