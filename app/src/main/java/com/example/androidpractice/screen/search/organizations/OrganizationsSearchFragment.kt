package com.example.androidpractice.screen.search.organizations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentOrganizationsSearchBinding
import com.example.androidpractice.screen.search.SearchResultAdapter
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.LeftPaddingDivider

class OrganizationsSearchFragment : BaseFragment<FragmentOrganizationsSearchBinding>(
    R.id.searchNavItem,
    FragmentOrganizationsSearchBinding::inflate
) {

    private val viewModel: OrganizationsSearchViewModel by viewModels()

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.organizationsResultsRV.adapter = adapter
        val dividerColor = requireContext().getColor(R.color.cool_grey)
        val dividerHeight = (resources.displayMetrics.density * 1).toInt()
        val decorator = LeftPaddingDivider(
            dividerHeight,
            dividerColor,
            requireContext()
        )
        binding.organizationsResultsRV.addItemDecoration(decorator)

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
            return OrganizationsSearchFragment()
        }
    }
}
