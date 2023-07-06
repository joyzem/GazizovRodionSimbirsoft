package com.example.androidpractice.ui

import android.app.Service
import androidx.fragment.app.Fragment
import com.example.androidpractice.App
import com.example.androidpractice.di.AppComponent

fun Fragment.getAppComponent(): AppComponent = (requireActivity().application as App).appComponent

fun Service.getAppComponent(): AppComponent = (applicationContext as App).appComponent
