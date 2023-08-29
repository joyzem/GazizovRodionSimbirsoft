package com.example.androidpractice.feature.news_details_impl

import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import javax.inject.Inject

class NewsDetailsFeatureApiImpl @Inject constructor() : NewsDetailsFeatureApi {
    override fun newsDetailsFragment(newsId: String) = NewsDetailsFragment.newInstance(newsId)
}