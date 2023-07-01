package com.example.androidpractice.screen.help

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentHelpBinding
import com.example.androidpractice.ui.BaseFragment
import com.example.androidpractice.ui.getAppComponent
import javax.inject.Inject

class HelpFragment :
    BaseFragment<FragmentHelpBinding>(R.id.helpNavItem, FragmentHelpBinding::inflate) {

    @Inject
    lateinit var viewModel: HelpViewModel

    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this)
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
