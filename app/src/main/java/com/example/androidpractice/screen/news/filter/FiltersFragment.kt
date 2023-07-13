package com.example.androidpractice.screen.news.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentFiltersBinding
import com.example.androidpractice.di.ViewModelFactory
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.LeftPaddingDivider
import com.example.androidpractice.ui.getAppComponent
import com.example.androidpractice.ui.navigation.findNavController
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiltersFragment : BaseFragment<FragmentFiltersBinding>(
    R.id.newsNavItem,
    FragmentFiltersBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: FiltersViewModel by viewModels {
        viewModelFactory
    }

    private val adapter by lazy {
        FiltersAdapter(viewModel::onFilterChecked)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().newsSubcomponent().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            filtersRV.adapter = adapter
            val dividerColor = requireContext().getColor(R.color.cool_grey)
            val dividerHeight = (resources.displayMetrics.density * 1).toInt()
            filtersRV.addItemDecoration(
                LeftPaddingDivider(
                    dividerHeight = dividerHeight,
                    color = dividerColor,
                    context = requireContext()
                )
            )

            filtersToolbar.setNavigationOnClickListener {
                findNavController().onBackPressed()
            }
            filtersToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filtersDoneActionButton -> {
                        viewModel.onApplyFilters()
                        findNavController().onBackPressed()
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.filters.observe(viewLifecycleOwner) { filters ->
                filters?.let { adapter.submitList(it) }
            }
        }
    }

    companion object {
        fun newInstance() = FiltersFragment()
    }
}
