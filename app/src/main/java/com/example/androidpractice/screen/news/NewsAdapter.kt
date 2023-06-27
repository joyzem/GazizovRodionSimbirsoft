package com.example.androidpractice.screen.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.R
import com.example.androidpractice.databinding.ItemEventBinding
import com.example.androidpractice.domain.model.Event

class NewsAdapter(private val onClick: (Event) -> Unit) :
    ListAdapter<Event, NewsAdapter.EventViewHolder>(EventDiffUtil) {

    class EventViewHolder(
        private val binding: ItemEventBinding,
        private val onClick: (Event) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            with(binding) {
                eventContainer.setOnClickListener {
                    onClick(event)
                }
                if (event.id == "1") {
                    eventImageView.setImageResource(R.drawable.img_event)
                } else {
                    eventImageView.setImageResource(R.drawable.img_event_2)
                }
                eventTitleTextView.text = event.title
                eventSubtitleTextView.text = event.subtitle
                eventDateTextView.text = getEventDateText(eventDateTextView, event.dates)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(
            binding,
            onClick
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    object EventDiffUtil : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
}
