package com.example.androidpractice.screen.help.di

import com.example.androidpractice.screen.help.HelpFragment
import dagger.Subcomponent

@Subcomponent(modules = [HelpModule::class])
interface HelpComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HelpComponent
    }

    fun inject(fragment: HelpFragment)
}
