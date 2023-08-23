package com.example.androidpractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidpractice.data.categories.local.CategoriesDao
import com.example.androidpractice.data.categories.local.CategoryEntity
import com.example.androidpractice.data.events.local.EventEntity
import com.example.androidpractice.data.events.local.EventsDao
import com.example.androidpractice.data.local.converters.LocalDateConverter

@Database(entities = [EventEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class WantHelpDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun eventsDao(): EventsDao
}