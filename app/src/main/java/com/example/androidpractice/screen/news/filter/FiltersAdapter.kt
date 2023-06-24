package com.example.androidpractice.screen.news.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.R
import com.google.android.material.switchmaterial.SwitchMaterial

class FiltersAdapter(private val onFilterChecked: (CategoryFilter) -> Unit) :
    ListAdapter<CategoryFilter, FiltersAdapter.FilterViewHolder>(FilterDiffUtil) {

    class FilterViewHolder(
        private val onFilterChecked: (CategoryFilter) -> Unit, view: View
    ) : ViewHolder(view) {

        private val switch: SwitchMaterial
        private var filter: CategoryFilter? = null

        init {
            switch = view.findViewById(R.id.filterSwitch)
            switch.setOnCheckedChangeListener { _, checked ->
                filter?.copy(checked = checked)?.let(onFilterChecked)
            }
        }

        fun bind(filter: CategoryFilter) {
            switch.text = filter.category.title
            switch.isChecked = filter.checked
            this.filter = filter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            onFilterChecked,
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_filter,
                parent,
                false
            )
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
