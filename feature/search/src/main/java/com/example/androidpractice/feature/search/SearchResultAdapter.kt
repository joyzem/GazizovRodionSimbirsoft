package com.example.androidpractice.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.feature.search.databinding.ItemSearchResultBinding

internal class SearchResultAdapter(private val onClick: (Event) -> Unit) :
    ListAdapter<Event, SearchResultAdapter.ResultViewHolder>(
        EventDiffUtil
    ) {

    class ResultViewHolder(
        private val binding: ItemSearchResultBinding,
        private val onClick: (Event) -> Unit
    ) : ViewHolder(binding.root) {

        private var event: Event? = null

        fun bind(event: Event) {
            with(binding.searchResultTextView) {
                text = event.title
                setOnClickListener {
                    onClick(event)
                }
            }
            this.event = event
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

    object EventDiffUtil : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.title == newItem.title
        }
    }
}
