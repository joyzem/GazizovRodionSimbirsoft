package com.example.androidpractice.screen.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentSearchBinding
import com.example.androidpractice.screen.search.events.EventsSearchFragment
import com.example.androidpractice.screen.search.organizations.OrganizationsSearchFragment
import com.example.androidpractice.ui.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.id.searchNavItem,
    FragmentSearchBinding::inflate
) {
    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchViewPager.adapter = SearchPagerAdapter(this@SearchFragment)
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

        setSearchViewRxBinding()
    }

    private fun setSearchViewRxBinding() {
        val subscription =
            binding.searchFieldContainer.searchView
                .queryTextChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .subscribe { query ->
                    sendSearchQueryResult(query)
                }
        compositeDisposable.add(subscription)
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

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {
        const val SEARCH_QUERY = "search_query"
        const val SEARCH_BY_KEYWORDS = "search_by_keywords"
        const val KEYWORDS = "keywords"

        fun newInstance() = SearchFragment()
    }
}
