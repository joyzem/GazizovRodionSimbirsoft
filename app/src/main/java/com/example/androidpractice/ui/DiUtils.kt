package com.example.androidpractice.ui

import androidx.fragment.app.Fragment
import com.example.androidpractice.App
import com.example.androidpractice.di.AppComponent

fun Fragment.getAppComponent(): AppComponent = (requireActivity().application as App).appComponent
