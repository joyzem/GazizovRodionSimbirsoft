package com.example.androidpractice.screen.help

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentHelpBinding
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HelpFragment : BaseFragment<FragmentHelpBinding, HelpViewModel>(
    R.id.helpNavItem,
    FragmentHelpBinding::inflate
) {
    override val viewModel: HelpViewModel by viewModels {
        viewModelFactory
    }

    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun injectViewModelFactory() {
        getAppComponent().helpSubcomponent().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.categoriesRecyclerView) {
            adapter = this@HelpFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.categories.collectLatest { categories ->
                categories?.let {
                    adapter.setData(it)
                    showCategories()
                }
            }
        }
    }

    private fun showCategories() {
        with(binding) {
            loadingProgress.isVisible = false
            categoriesRecyclerView.isVisible = true
        }
    }

    companion object {
        fun newInstance(): HelpFragment {
            return HelpFragment()
        }
    }
}
