package com.example.androidpractice.screen.help

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentHelpBinding
import com.example.androidpractice.di.ViewModelsFactoryOwner
import com.example.androidpractice.di.getViewModel
import com.example.androidpractice.ui.BaseFragment

class HelpFragment :
    BaseFragment<FragmentHelpBinding>(R.id.helpNavItem, FragmentHelpBinding::inflate) {

    private lateinit var viewModel: HelpViewModel

    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as ViewModelsFactoryOwner).getViewModel()
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
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.setData(categories)
        }
    }

    companion object {
        fun newInstance(): HelpFragment {
            return HelpFragment()
        }
    }
}
