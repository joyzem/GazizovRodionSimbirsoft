package com.example.androidpractice.screen.search.events

import android.content.Context
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
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentEventsSearchBinding
import com.example.androidpractice.di.ViewModelFactory
import com.example.androidpractice.screen.search.SearchFragment
import com.example.androidpractice.screen.search.SearchResultAdapter
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.LeftPaddingDivider
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.ui.spans.ClickableText
import javax.inject.Inject

class EventsSearchFragment : BaseFragment<FragmentEventsSearchBinding>(
    R.id.searchNavItem,
    FragmentEventsSearchBinding::inflate
) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EventsSearchViewModel by viewModels {
        viewModelFactory
    }

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {
        }
    }

    private var scrollPosition: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().searchSubcomponent().create().inject(this)
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
            eventsResultsRV.adapter = adapter
            eventsResultsRV.addItemDecoration(createDecorator())

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

    private fun createDecorator(): RecyclerView.ItemDecoration {
        val dividerColor = requireContext().getColor(R.color.cool_grey)
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
            ForegroundColorSpan(resources.getColor(R.color.leaf, null)),
            forExampleLentgh,
            length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        setSpan(
            ClickableText {
                requireActivity().supportFragmentManager.setFragmentResult(
                    "searchKeywords", bundleOf(
                        "keywords" to keywordsExample
                    )
                )
            },
            forExampleLentgh,
            length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
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
        const val SEARCH_KEY = "events"
        private const val SCROLL_POSITION = "scroll_position"

        fun newInstance(): Fragment {
            return EventsSearchFragment()
        }
    }
}
