package com.example.androidpractice.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.searchTabLayout
        val viewPager = binding.searchViewPager
        viewPager.adapter = SearchPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.by_events)
            } else {
                tab.text = getString(R.string.by_organizations)
            }
        }.attach()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.searchToolbar.menu[0].setOnMenuItemClickListener {
            binding.searchToolbar.visibility = View.GONE
            binding.searchFieldContainer.root.visibility = View.VISIBLE
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
