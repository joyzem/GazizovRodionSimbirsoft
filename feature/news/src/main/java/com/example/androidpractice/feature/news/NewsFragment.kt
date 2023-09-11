package com.example.androidpractice.feature.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.navigation.findNavController
import com.example.androidpractice.feature.news.databinding.FragmentNewsBinding
import com.example.androidpractice.feature.news.filter.FiltersFragment
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import javax.inject.Inject

class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(
    com.example.androidpractice.core.ui.R.id.newsNavItem,
    FragmentNewsBinding::inflate
) {
    override val viewModel: NewsViewModel by viewModels {
        viewModelFactory
    }

    private val componentViewModel by viewModels<NewsComponentViewModel>()

    @Inject
    lateinit var detailsApi: NewsDetailsFeatureApi

    override fun injectViewModelFactory() {
        componentViewModel.newsComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsScreen.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setContent {
                AppTheme {
                    val news by viewModel.events.collectAsState()
                    NewsScreen(
                        news = news,
                        onEventClick = { id ->
                            findNavController().navigate(
                                detailsApi.newsDetailsFragment(id)
                            )
                        },
                        onFiltersClick = {
                            findNavController().navigate(
                                FiltersFragment.newInstance()
                            )
                        }
                    )
                }
            }
        }

        if (savedInstanceState == null) {
            val getEventsIntent = Intent(activity, GetEventsService::class.java)
            activity?.startService(getEventsIntent)
        }
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
