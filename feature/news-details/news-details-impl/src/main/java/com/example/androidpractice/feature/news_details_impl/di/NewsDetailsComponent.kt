package com.example.androidpractice.feature.news_details_impl.di

import com.example.androidpractice.feature.news_details_impl.NewsDetailsFragment
import com.example.androidpractice.feature.news_details_impl.money.DonationDialog
import dagger.Component

@Component(dependencies = [NewsDetailsDeps::class])
internal interface NewsDetailsComponent {

    @Component.Builder
    interface Builder {
        fun deps(deps: NewsDetailsDeps): Builder
        fun create(): NewsDetailsComponent
    }

    fun inject(fragment: NewsDetailsFragment)

    fun inject(dialog: DonationDialog)
}
