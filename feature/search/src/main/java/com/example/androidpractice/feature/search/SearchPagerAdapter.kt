package com.example.androidpractice.feature.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidpractice.feature.search.events.EventsSearchFragment
import com.example.androidpractice.feature.search.organizations.OrganizationsSearchFragment

internal class SearchPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            EventsSearchFragment.newInstance()
        } else {
            OrganizationsSearchFragment.newInstance()
        }
    }
}
