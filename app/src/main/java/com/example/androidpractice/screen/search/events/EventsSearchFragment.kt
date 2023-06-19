package com.example.androidpractice.screen.search.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentEventsSearchBinding
import com.example.androidpractice.screen.search.SearchResultAdapter
import com.example.androidpractice.ui.LeftPaddingDivider

class EventsSearchFragment : Fragment() {
    private var _binding: FragmentEventsSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventsSearchViewModel by viewModels()

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventsResultsRV.adapter = adapter
        val dividerColor = requireContext().getColor(R.color.cool_grey)
        val dividerHeight = (resources.displayMetrics.density * 1).toInt()
        val decorator = LeftPaddingDivider(
            dividerHeight, dividerColor, requireContext()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment {
            return EventsSearchFragment()
        }
    }
}