package com.example.androidpractice.feature.search.organizations

import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.LeftPaddingDivider
import com.example.androidpractice.core.ui.spans.ClickableText
import com.example.androidpractice.feature.search.R
import com.example.androidpractice.feature.search.SearchComponentViewModel
import com.example.androidpractice.feature.search.SearchFragment
import com.example.androidpractice.feature.search.SearchFragment.Companion.KEYWORDS
import com.example.androidpractice.feature.search.SearchFragment.Companion.SEARCH_BY_KEYWORDS
import com.example.androidpractice.feature.search.SearchResultAdapter
import com.example.androidpractice.feature.search.databinding.FragmentOrganizationsSearchBinding
import com.example.androidpractice.core.designsystem.R as designR

class OrganizationsSearchFragment :
    BaseFragment<FragmentOrganizationsSearchBinding, OrganizationsSearchViewModel>(
        R.id.searchNavigation,
        FragmentOrganizationsSearchBinding::inflate
    ) {
    override val viewModel: OrganizationsSearchViewModel by viewModels {
        viewModelFactory
    }

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {
        }
    }

    private var scrollPosition = 0

    override fun injectViewModelFactory() {
        ViewModelProvider(this).get<SearchComponentViewModel>().searchComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            SEARCH_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            viewModel.search(bundle.getString(SearchFragment.SEARCH_QUERY, ""))
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            organizationsResultsRV.adapter = adapter
            organizationsResultsRV.addItemDecoration(createDecorator())
            noResultsLayout.keyWordsTextView.text = getKeywordsText()
            noResultsLayout.keyWordsTextView.movementMethod = LinkMovementMethod()

            root.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                scrollPosition = scrollY
            }
        }

        observe()

        savedInstanceState?.let { bundle ->
            scrollPosition = bundle.getInt(SCROLL_POSITION, 0)
            binding.root.post {
                binding.root.scrollTo(0, scrollPosition)
            }
        }
    }

    private fun observe() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                showSearchResults()
                adapter.submitList(result.events)
                binding.resultsTextView.text = requireContext().resources.getQuantityString(
                    R.plurals.search_results,
                    result.events.size,
                    result.events.size
                )
                binding.keyWordsTextView.text = getString(
                    R.string.keywords_are,
                    result.keywords.joinToString()
                )
            } else {
                showEmptyScreen()
            }
        }
    }

    private fun createDecorator(): ItemDecoration {
        val dividerColor = requireContext().getColor(designR.color.cool_grey)
        val dividerHeight = (resources.displayMetrics.density * 1).toInt()
        return LeftPaddingDivider(
            dividerHeight,
            dividerColor,
            requireContext()
        )
    }

    private fun getKeywordsText() = buildSpannedString {
        append(getString(R.string.for_example))
        append(" ")
        val forExampleLentgh = length
        val keywordsExample = getString(R.string.keywords_example)
        append(keywordsExample)
        setSpan(
            ForegroundColorSpan(resources.getColor(designR.color.leaf, null)),
            forExampleLentgh,
            length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        setSpan(
            ClickableText {
                requireActivity().supportFragmentManager.setFragmentResult(
                    SEARCH_BY_KEYWORDS,
                    bundleOf(
                        KEYWORDS to keywordsExample
                    )
                )
            },
            forExampleLentgh,
            length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

    private fun showSearchResults() {
        with(binding) {
            searchResultsLinearLayout.isVisible = true
            noResultsLayout.root.isVisible = false
        }
    }

    private fun showEmptyScreen() {
        with(binding) {
            noResultsLayout.root.isVisible = true
            searchResultsLinearLayout.isVisible = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_POSITION, scrollPosition)
    }

    companion object {
        const val SEARCH_KEY = "organizations"
        private const val SCROLL_POSITION = "scroll_position"

        fun newInstance(): Fragment {
            return OrganizationsSearchFragment()
        }
    }
}
