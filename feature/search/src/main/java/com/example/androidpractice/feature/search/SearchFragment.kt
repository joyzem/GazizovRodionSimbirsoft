package com.example.androidpractice.feature.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewpager2.widget.ViewPager2
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.feature.search.databinding.FragmentSearchBinding
import com.example.androidpractice.feature.search.events.EventsSearchFragment
import com.example.androidpractice.feature.search.organizations.OrganizationsSearchFragment
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    R.id.searchNavigation,
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun injectViewModelFactory() {
        ViewModelProvider(this).get<SearchComponentViewModel>().searchComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchViewPager.adapter = SearchPagerAdapter(this@SearchFragment)
            searchViewPager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        searchFieldContainer.searchView.queryHint = when (position) {
                            0 -> getString(R.string.enter_event_name)
                            else -> getString(R.string.enter_organization_name)
                        }
                    }

                    override fun onPageSelected(position: Int) {}

                    override fun onPageScrollStateChanged(state: Int) {}
                }
            )
            TabLayoutMediator(searchTabLayout, searchViewPager) { tab, position ->
                if (position == 0) {
                    tab.text = getString(R.string.by_events)
                } else {
                    tab.text = getString(R.string.by_organizations)
                }
            }.attach()

            searchFieldContainer.searchButton.setOnClickListener {
                sendSearchQueryResult(searchFieldContainer.searchView.query.toString())
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            SEARCH_BY_KEYWORDS,
            viewLifecycleOwner
        ) { _, bundle ->
            val keywords = bundle.getString(KEYWORDS, "")
            binding.searchFieldContainer.searchView.setQuery(keywords, true)
        }

        setSearchViewQueryListener()

        observe()
    }

    private fun observe() {
        viewModel.queries.observe(viewLifecycleOwner) {
            sendSearchQueryResult(it)
        }
    }

    private fun setSearchViewQueryListener() {
        binding.searchFieldContainer.searchView
            .setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.newQuery(query ?: "")
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.newQuery(newText ?: "")
                        return true
                    }
                }
            )
    }

    private fun sendSearchQueryResult(query: String) {
        val key = when (binding.searchViewPager.currentItem) {
            0 -> EventsSearchFragment.SEARCH_KEY
            else -> OrganizationsSearchFragment.SEARCH_KEY
        }
        setFragmentResult(
            key,
            bundleOf(
                SEARCH_QUERY to query
            )
        )
    }

    companion object {
        const val SEARCH_QUERY = "search_query"
        const val SEARCH_BY_KEYWORDS = "search_by_keywords"
        const val KEYWORDS = "keywords"

        fun newInstance() = SearchFragment()
    }
}
