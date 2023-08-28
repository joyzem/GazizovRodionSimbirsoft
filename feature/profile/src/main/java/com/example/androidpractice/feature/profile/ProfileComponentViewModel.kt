package com.example.androidpractice.feature.profile

import androidx.lifecycle.ViewModel
import com.example.androidpractice.feature.profile.di.ProfileComponent
import com.example.androidpractice.feature.profile.di.DaggerProfileComponent
import com.example.androidpractice.feature.profile.di.ProfileDepsProvider

internal class ProfileComponentViewModel : ViewModel() {

    val profileComponent: ProfileComponent = DaggerProfileComponent.builder().deps(ProfileDepsProvider.deps).create()
}