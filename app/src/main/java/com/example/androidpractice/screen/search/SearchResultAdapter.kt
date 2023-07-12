package com.example.androidpractice.screen.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.databinding.ItemSearchResultBinding
import com.example.androidpractice.domain.model.Event

class SearchResultAdapter(private val onClick: (Event) -> Unit) :
    ListAdapter<Event, SearchResultAdapter.ResultViewHolder>(
        Event.EventDiffUtil
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
}
