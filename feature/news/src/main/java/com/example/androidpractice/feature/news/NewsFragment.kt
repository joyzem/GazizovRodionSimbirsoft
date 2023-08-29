package com.example.androidpractice.feature.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.navigation.findNavController
import com.example.androidpractice.feature.news.databinding.FragmentNewsBinding
import com.example.androidpractice.feature.news.filter.FiltersFragment
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import javax.inject.Inject

class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(
    R.id.newsNavigation,
    FragmentNewsBinding::inflate
) {
    override val viewModel: NewsViewModel by viewModels {
        viewModelFactory
    }

    private val componentViewModel by viewModels<NewsComponentViewModel>()

    @Inject
    lateinit var detailsApi: NewsDetailsFeatureApi

    private val adapter by lazy {
        NewsAdapter(onClick = {
            findNavController().navigate(
                detailsApi.newsDetailsFragment(it.id)
            )
        })
    }

    override fun injectViewModelFactory() {
        componentViewModel.newsComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            newsRV.adapter = adapter
            newsToolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.newsFilterActionButton -> {
                        findNavController().navigate(
                            FiltersFragment.newInstance()
                        )
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }

        observeLiveData()

        if (savedInstanceState == null) {
            val getEventsIntent = Intent(activity, GetEventsService::class.java)
            activity?.startService(getEventsIntent)
        }
    }

    private fun observeLiveData() {
        viewModel.events.observe(viewLifecycleOwner, ::showEvents)
    }

    private fun showEvents(events: List<Event>?) {
        if (events != null) {
            adapter.submitList(events)
            with(binding) {
                progressCircular.isVisible = false
                newsRV.isVisible = true
            }
        }
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
