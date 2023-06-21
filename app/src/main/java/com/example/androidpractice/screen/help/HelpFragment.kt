package com.example.androidpractice.screen.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HelpViewModel by viewModels()

    private val adapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriesRecyclerView.adapter = adapter
        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
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
