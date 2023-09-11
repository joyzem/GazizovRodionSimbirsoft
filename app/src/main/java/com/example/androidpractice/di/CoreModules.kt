package com.example.androidpractice.di

import com.example.androidpractice.core.data.di.AssetsModule
import com.example.androidpractice.core.data.di.InteractorModule
import com.example.androidpractice.core.database.di.DatabaseModule
import com.example.androidpractice.core.network.di.NetworkModule
import com.example.androidpractice.core.network.di.RetrofitModule
import dagger.Module

@Module(
    includes = [
        AssetsModule::class,
        NetworkModule::class,
        RetrofitModule::class,
        DatabaseModule::class,
        InteractorModule::class
    ]
)
interface CoreModules
