package com.example.androidpractice.feature.news.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.LeftPaddingDivider
import com.example.androidpractice.core.ui.navigation.findNavController
import com.example.androidpractice.feature.news.NewsComponentViewModel
import com.example.androidpractice.feature.news.R
import com.example.androidpractice.feature.news.databinding.FragmentFiltersBinding
import kotlinx.coroutines.launch
import com.example.androidpractice.core.designsystem.R as designR

class FiltersFragment : BaseFragment<FragmentFiltersBinding, FiltersViewModel>(
    R.id.newsNavigation,
    FragmentFiltersBinding::inflate
) {
    override val viewModel: FiltersViewModel by viewModels {
        viewModelFactory
    }

    private val adapter by lazy {
        FiltersAdapter(viewModel::onFilterChecked)
    }

    override fun injectViewModelFactory() {
        ViewModelProvider(this).get<NewsComponentViewModel>().newsComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            filtersRV.adapter = adapter
            val dividerColor = requireContext().getColor(designR.color.cool_grey)
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
