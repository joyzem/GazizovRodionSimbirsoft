package com.example.androidpractice.screen.news.filter

import android.os.Bundle
import android.view.View
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentFiltersBinding
import com.example.androidpractice.di.ViewModelsFactoryOwner
import com.example.androidpractice.di.getViewModel
import com.example.androidpractice.screen.news.NewsViewModel
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.LeftPaddingDivider
import com.example.androidpractice.ui.navigation.findNavController

class FiltersFragment :
    BaseFragment<FragmentFiltersBinding>(R.id.newsNavItem, FragmentFiltersBinding::inflate) {

    private lateinit var viewModel: NewsViewModel

    private val adapter by lazy {
        FiltersAdapter(viewModel::onFilterChecked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as ViewModelsFactoryOwner).getViewModel()
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
                        findNavController().onBackPressed()
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }

        viewModel.filters.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = FiltersFragment()
    }
}