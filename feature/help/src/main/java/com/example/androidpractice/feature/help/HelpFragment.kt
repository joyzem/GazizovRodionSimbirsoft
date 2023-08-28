package com.example.androidpractice.feature.help

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.core.model.category.Category
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.feature.help.databinding.FragmentHelpBinding

class HelpFragment : BaseFragment<FragmentHelpBinding, HelpViewModel>(
    R.id.HelpNavigation,
    FragmentHelpBinding::inflate
) {
    override val viewModel: HelpViewModel by viewModels {
        viewModelFactory
    }

    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun injectViewModelFactory() {
        ViewModelProvider(this).get<HelpComponentViewModel>().helpComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.categoriesRecyclerView) {
            adapter = this@HelpFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        with(viewModel) {
            categories.observe(viewLifecycleOwner, ::showCategories)
        }
    }

    private fun showCategories(categories: List<Category>?) {
        if (!categories.isNullOrEmpty()) {
            adapter.setData(categories)
            with(binding) {
                loadingProgress.isVisible = false
                categoriesRecyclerView.isVisible = true
            }
        }
    }

    companion object {
        fun newInstance(): HelpFragment {
            return HelpFragment()
        }
    }
}
