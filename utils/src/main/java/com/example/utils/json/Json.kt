package com.example.utils.json

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedInputStream

inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(
        json,
        object : TypeToken<T>() {}.type
    )
}

fun getJsonFromAssets(assetManager: AssetManager, fileName: String): String {
    val inputStream = BufferedInputStream(assetManager.open(fileName))
    var json: String
    inputStream.use { stream ->
        val size = stream.available()
        val content = ByteArray(size)
        stream.read(content)
        json = String(content)
    }
    return json
}
