package com.example.androidpractice.screen.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentNewsBinding
import com.example.androidpractice.screen.news.details.EventDetailsFragment
import com.example.androidpractice.screen.news.filter.FiltersFragment
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.ui.navigation.findNavController

class NewsFragment : BaseFragment<FragmentNewsBinding>(
    R.id.newsNavItem,
    FragmentNewsBinding::inflate
) {

    private val viewModel: NewsViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }

    private val adapter by lazy {
        NewsAdapter {
            findNavController().navigate(
                EventDetailsFragment.newInstance(it.id)
            )
        }
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

        observe()

        if (savedInstanceState == null) {
            val getEventsIntent = Intent(activity, GetEventsService::class.java)
            activity?.startService(getEventsIntent)
        }
    }

    private fun observe() {
        viewModel.events.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                adapter.submitList(events)
                showEvents()
            }
        }
    }

    private fun showEvents() {
        with(binding) {
            progressCircular.isVisible = false
            newsRV.isVisible = true
        }
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
