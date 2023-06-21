package com.example.androidpractice.screen.help

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.R
import com.example.androidpractice.domain.model.Category

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var dataset: List<Category> = listOf()

    class CategoryViewHolder(view: View) : ViewHolder(view) {

        private var categoryImage: ImageView
        private var title: TextView

        init {
            categoryImage = view.findViewById(R.id.categoryImageView)
            title = view.findViewById(R.id.categoryTextView)
        }

        fun bind(category: Category) {
            categoryImage.setImageResource(category.imageId)
            title.setText(category.titleId)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(categories: List<Category>) {
        dataset = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_help_category, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(dataset[position])
    }
}
