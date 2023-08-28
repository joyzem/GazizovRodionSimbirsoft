package com.example.androidpractice.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidpractice.core.database.categories.CategoriesDao
import com.example.androidpractice.core.database.categories.CategoryEntity
import com.example.androidpractice.core.database.converters.LocalDateConverter
import com.example.androidpractice.core.database.events.EventEntity
import com.example.androidpractice.core.database.events.EventsDao

@Database(entities = [EventEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class WantHelpDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun eventsDao(): EventsDao
}
