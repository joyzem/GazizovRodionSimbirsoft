package com.example.androidpractice.screen.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.navigation.findNavController
import com.example.androidpractice.databinding.FragmentNewsBinding
import com.example.androidpractice.screen.news.details.EventDetailsFragment
import com.example.androidpractice.screen.news.filter.FiltersFragment
import com.example.androidpractice.ui.getAppComponent

class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(
    R.id.newsNavItem,
    FragmentNewsBinding::inflate
) {
    override val viewModel: NewsViewModel by viewModels {
        viewModelFactory
    }

    private val adapter by lazy {
        NewsAdapter {
            findNavController().navigate(
                EventDetailsFragment.newInstance(it.id)
            )
        }
    }

    override fun injectViewModelFactory() {
        getAppComponent().newsSubcomponent().create().inject(this)
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
