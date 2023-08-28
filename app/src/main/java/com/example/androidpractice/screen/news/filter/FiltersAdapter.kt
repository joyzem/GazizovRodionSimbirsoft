package com.example.androidpractice.screen.news.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.core.model.category.CategoryFilter
import com.example.androidpractice.databinding.ItemFilterBinding

class FiltersAdapter(private val onFilterChecked: (CategoryFilter) -> Unit) :
    ListAdapter<CategoryFilter, FiltersAdapter.FilterViewHolder>(FilterDiffUtil) {

    class FilterViewHolder(
        private val binding: ItemFilterBinding,
        private val onFilterChecked: (CategoryFilter) -> Unit
    ) : ViewHolder(binding.root) {

        private var filter: CategoryFilter? = null

        init {
            binding.filterSwitch.setOnCheckedChangeListener { _, checked ->
                filter?.copy(checked = checked)?.let(onFilterChecked)
            }
        }

        fun bind(filter: CategoryFilter) {
            this.filter = filter
            with(binding.filterSwitch) {
                text = filter.category.title
                isChecked = filter.checked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilterViewHolder(
            binding,
            onFilterChecked
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(currentList[holder.adapterPosition])
    }

    object FilterDiffUtil : DiffUtil.ItemCallback<CategoryFilter>() {
        override fun areItemsTheSame(oldItem: CategoryFilter, newItem: CategoryFilter): Boolean {
            return oldItem.category.id == newItem.category.id
        }

        override fun areContentsTheSame(oldItem: CategoryFilter, newItem: CategoryFilter): Boolean {
            return oldItem == newItem
        }
    }
}
