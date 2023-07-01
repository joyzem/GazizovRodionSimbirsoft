package com.example.androidpractice.screen.news

import android.os.Bundle
import android.view.View
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentNewsBinding
import com.example.androidpractice.screen.news.details.EventDetailsFragment
import com.example.androidpractice.screen.news.filter.FiltersFragment
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.ui.navigation.findNavController
import javax.inject.Inject

class NewsFragment : BaseFragment<FragmentNewsBinding>(
    R.id.newsNavItem,
    FragmentNewsBinding::inflate
) {

    @Inject
    lateinit var viewModel: NewsViewModel

    private val adapter by lazy {
        NewsAdapter {
            findNavController().navigate(
                EventDetailsFragment.newInstance(it.id)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this)
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

        viewModel.events.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
