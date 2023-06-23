package com.example.androidpractice.di

import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides

@Module
object AssetsModule {

    @Provides
    fun provideAssets(context: Context): AssetManager = context.assets
}
