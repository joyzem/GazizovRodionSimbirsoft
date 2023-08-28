package com.example.androidpractice.feature.help

import androidx.lifecycle.ViewModel
import com.example.androidpractice.feature.help.di.DaggerHelpComponent
import com.example.androidpractice.feature.help.di.HelpDepsProvider

internal class HelpComponentViewModel : ViewModel() {
    val helpComponent = DaggerHelpComponent.builder().deps(HelpDepsProvider.deps).create()
}