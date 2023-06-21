package com.example.androidpractice.screen.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidpractice.screen.search.events.EventsSearchFragment
import com.example.androidpractice.screen.search.organizations.OrganizationsSearchFragment

class SearchPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            EventsSearchFragment.newInstance()
        } else {
            OrganizationsSearchFragment.newInstance()
        }
    }
}
