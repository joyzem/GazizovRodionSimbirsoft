package com.example.androidpractice.screen.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentSearchBinding
import com.example.androidpractice.screen.search.events.EventsSearchFragment
import com.example.androidpractice.screen.search.organizations.OrganizationsSearchFragment
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    R.id.searchNavItem,
    FragmentSearchBinding::inflate
) {
    private val compositeDisposable = CompositeDisposable()

    override val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchViewPager.adapter = SearchPagerAdapter(this@SearchFragment)
            searchViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
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
            })
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

        observe()
    }

    private fun observe() {
        compositeDisposable.add(
            viewModel.queries.subscribe {
                sendSearchQueryResult(it)
            }
        )
    }

    private fun setSearchViewRxBinding() {
        compositeDisposable.add(
            binding.searchFieldContainer.searchView
                .queryTextChanges()
                .skipInitialValue()
                .subscribe { query ->
                    viewModel.newQuery(query)
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

    override fun injectViewModelFactory() {
        getAppComponent().searchSubcomponent().create().inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    companion object {
        const val SEARCH_QUERY = "search_query"
        const val SEARCH_BY_KEYWORDS = "search_by_keywords"
        const val KEYWORDS = "keywords"

        fun newInstance() = SearchFragment()
    }
}
