package com.example.androidpractice.screen.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.databinding.ItemSearchResultBinding
import com.example.androidpractice.domain.model.SearchResult

class SearchResultAdapter(private val onClick: (SearchResult) -> Unit) :
    ListAdapter<SearchResult, SearchResultAdapter.ResultViewHolder>(
        SearchDiffUtil
    ) {

    class ResultViewHolder(
        private val binding: ItemSearchResultBinding,
        private val onClick: (SearchResult) -> Unit
    ) : ViewHolder(binding.root) {

        private var result: SearchResult? = null

        fun bind(result: SearchResult) {
            with(binding.searchResultTextView) {
                text = result.resultName
                setOnClickListener {
                    onClick(result)
                }
            }
            this.result = result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object SearchDiffUtil : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem == newItem
        }
    }
}
