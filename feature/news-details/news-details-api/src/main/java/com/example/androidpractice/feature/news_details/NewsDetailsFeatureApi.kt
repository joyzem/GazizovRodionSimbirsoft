package com.example.androidpractice.feature.news_details

import com.example.androidpractice.core.ui.BaseFragment

interface NewsDetailsFeatureApi {
    fun newsDetailsFragment(newsId: String): BaseFragment<*, *>
}