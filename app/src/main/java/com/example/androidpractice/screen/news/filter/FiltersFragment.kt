package com.example.androidpractice.screen.news.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidpractice.core.designsystem.R
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.LeftPaddingDivider
import com.example.androidpractice.core.ui.navigation.findNavController
import com.example.androidpractice.databinding.FragmentFiltersBinding
import com.example.androidpractice.ui.getAppComponent
import kotlinx.coroutines.launch

class FiltersFragment : BaseFragment<FragmentFiltersBinding, FiltersViewModel>(
    com.example.androidpractice.R.id.newsNavItem,
    FragmentFiltersBinding::inflate
) {
    override val viewModel: FiltersViewModel by viewModels {
        viewModelFactory
    }

    private val adapter by lazy {
        FiltersAdapter(viewModel::onFilterChecked)
    }

    override fun injectViewModelFactory() {
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
                    com.example.androidpractice.R.id.filtersDoneActionButton -> {
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
