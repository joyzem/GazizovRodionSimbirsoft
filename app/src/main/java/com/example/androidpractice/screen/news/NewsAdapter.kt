package com.example.androidpractice.screen.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.request.Disposable
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.core.ui.extensions.loadWithoutCaching
import com.example.androidpractice.databinding.ItemEventBinding

class NewsAdapter(private val onClick: (Event) -> Unit) :
    ListAdapter<Event, NewsAdapter.EventViewHolder>(EventDiffUtil) {

    class EventViewHolder(
        private val binding: ItemEventBinding,
        private val onClick: (Event) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var disposable: Disposable? = null

        fun bind(event: Event) {
            with(binding) {
                eventContainer.setOnClickListener {
                    onClick(event)
                }
                disposable = eventImageView.loadWithoutCaching(event.imagePreview)
                eventTitleTextView.text = event.title
                eventSubtitleTextView.text = event.subtitle
                eventDateTextView.text =
                    getEventDateText(eventDateTextView, event.startDate, event.endDate)
            }
        }

        fun dispose() {
            disposable?.dispose()
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

    override fun onViewRecycled(holder: EventViewHolder) {
        super.onViewRecycled(holder)
        // Dispose coil request
        holder.dispose()
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
