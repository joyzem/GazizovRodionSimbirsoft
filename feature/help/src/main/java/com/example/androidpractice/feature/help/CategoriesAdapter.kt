package com.example.androidpractice.feature.help

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.core.model.category.Category
import com.example.androidpractice.core.ui.extensions.loadWithoutCaching
import com.example.androidpractice.feature.help.databinding.ItemHelpCategoryBinding

internal class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var dataset: List<Category> = listOf()

    class CategoryViewHolder(private val binding: ItemHelpCategoryBinding) :
        ViewHolder(binding.root) {

        fun bind(category: Category) {
            with(binding) {
                categoryImageView.loadWithoutCaching(category.imageUrl)
                categoryTextView.text = category.title
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(categories: List<Category>) {
        dataset = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemHelpCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(dataset[position])
    }
}
