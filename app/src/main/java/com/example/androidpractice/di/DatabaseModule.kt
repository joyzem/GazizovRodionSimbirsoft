package com.example.androidpractice.di

import android.content.Context
import androidx.room.Room
import com.example.androidpractice.data.local.WantHelpDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): WantHelpDatabase {
        return Room.databaseBuilder(
            context,
            WantHelpDatabase::class.java,
            "want-help.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCategoriesDao(database: WantHelpDatabase) = database.categoriesDao()

    @Provides
    @Singleton
    fun provideEventsDao(database: WantHelpDatabase) = database.eventsDao()
}