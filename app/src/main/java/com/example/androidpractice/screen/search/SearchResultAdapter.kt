package com.example.androidpractice.screen.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.R
import com.example.androidpractice.domain.model.SearchResult

class SearchResultAdapter(private val onClick: (SearchResult) -> Unit) :
    ListAdapter<SearchResult, SearchResultAdapter.ResultViewHolder>(
        DIFF_UTIL
    ) {

    class ResultViewHolder(
        view: View,
        private val onClick: (SearchResult) -> Unit
    ) : ViewHolder(view) {
        private val resultTV: TextView
        private var result: SearchResult? = null

        init {
            resultTV = view.findViewById(R.id.searchResultTextView)
            resultTV.setOnClickListener {
                result?.let(onClick)
            }
        }

        fun bind(result: SearchResult) {
            resultTV.text = result.resultName
            this.result = result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ResultViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DIFF_UTIL : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem == newItem
        }
    }
}
