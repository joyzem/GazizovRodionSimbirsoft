package com.example.androidpractice.screen.search

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentSearchBinding
import com.example.androidpractice.ui.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.id.searchNavItem,
    FragmentSearchBinding::inflate
) {

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
            setClickListeners()
        }
    }

    private fun setClickListeners() {
        binding.searchToolbar.menu[0].setOnMenuItemClickListener {
            binding.searchToolbar.visibility = View.GONE
            binding.searchFieldContainer.root.visibility = View.VISIBLE
            true
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
