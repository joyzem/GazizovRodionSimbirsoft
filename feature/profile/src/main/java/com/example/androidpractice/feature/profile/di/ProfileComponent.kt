package com.example.androidpractice.feature.profile.di

import com.example.androidpractice.core.di.FeatureScope
import com.example.androidpractice.feature.profile.ProfileFragment
import dagger.Component

@ProfileFeature
@Component(dependencies = [ProfileDeps::class])
internal interface ProfileComponent {

    @Component.Builder
    interface Builder {
        fun deps(deps: ProfileDeps): Builder
        fun create(): ProfileComponent
    }

    fun inject(fragment: ProfileFragment)
}

@FeatureScope
annotation class ProfileFeature
