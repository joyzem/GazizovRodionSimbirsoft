package com.example.androidpractice.screen.help

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentHelpBinding
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HelpFragment :
    BaseFragment<FragmentHelpBinding>(R.id.helpNavItem, FragmentHelpBinding::inflate) {

    private val viewModel: HelpViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }
    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
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
            loadingProgress.visibility = View.GONE
            categoriesRecyclerView.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance(): HelpFragment {
            return HelpFragment()
        }
    }
}
