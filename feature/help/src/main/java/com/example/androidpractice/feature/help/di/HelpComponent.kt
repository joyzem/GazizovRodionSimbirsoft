package com.example.androidpractice.feature.help.di

import com.example.androidpractice.core.di.FeatureScope
import com.example.androidpractice.feature.help.HelpFragment
import dagger.Component

@Component(dependencies = [HelpDeps::class])
@HelpFeatureScope
internal interface HelpComponent {

    fun inject(fragment: HelpFragment)

    @Component.Builder
    interface Builder {
        fun deps(deps: HelpDeps): Builder
        fun create(): HelpComponent
    }
}

@FeatureScope
annotation class HelpFeatureScope
