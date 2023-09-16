package com.example.androidpractice

import android.content.Intent
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import com.example.androidpractice.feature.news_details_impl.NewsDetailsFragment

class DeepLinkManager {
    fun getDeepLinkFragmentOrDefault(
        intent: Intent?,
        default: BaseFragment<*, *>
    ): BaseFragment<*, *> {
        return intent?.let {
            if (intent.hasExtra(NewsDetailsFeatureApi.EVENT_ID_KEY)) {
                NewsDetailsFragment.newInstance(
                    intent.getStringExtra(
                        NewsDetailsFeatureApi.EVENT_ID_KEY
                    )!!
                )
            } else {
                default
            }
        } ?: default
    }
}
