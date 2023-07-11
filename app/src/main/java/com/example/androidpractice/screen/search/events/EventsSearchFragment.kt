package com.example.androidpractice.screen.search.events

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentEventsSearchBinding
import com.example.androidpractice.screen.search.SearchResultAdapter
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.LeftPaddingDivider
import com.example.androidpractice.ui.getAppComponent

class EventsSearchFragment : BaseFragment<FragmentEventsSearchBinding>(
    R.id.searchNavItem,
    FragmentEventsSearchBinding::inflate
) {
    private val viewModel: EventsSearchViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventsResultsRV.adapter = adapter
        val dividerColor = requireContext().getColor(R.color.cool_grey)
        val dividerHeight = (resources.displayMetrics.density * 1).toInt()
        val decorator = LeftPaddingDivider(
            dividerHeight,
            dividerColor,
            requireContext()
        )
        binding.eventsResultsRV.addItemDecoration(decorator)

        observe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.search("")
    }

    private fun observe() {
        viewModel.searchResults.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.resultsTextView.text = requireContext().resources.getQuantityString(
                R.plurals.search_results,
                it.size,
                it.size
            )
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return EventsSearchFragment()
        }
    }
}
