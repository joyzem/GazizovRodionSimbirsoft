package com.example.androidpractice.screen.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.R
import com.example.androidpractice.domain.model.Event

class NewsAdapter(private val onClick: (Event) -> Unit) :
    ListAdapter<Event, NewsAdapter.EventViewHolder>(EventDiffUtil) {

    class EventViewHolder(
        private val onClick: (Event) -> Unit,
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val container: CardView
        private val imageView: ImageView
        private val title: TextView
        private val subtitle: TextView
        private val date: TextView

        init {
            container = view.findViewById(R.id.eventContainer)
            imageView = view.findViewById(R.id.eventImageView)
            title = view.findViewById(R.id.eventTitleTextView)
            subtitle = view.findViewById(R.id.eventSubtitleTextView)
            date = view.findViewById(R.id.eventDateTextView)
        }

        fun bind(event: Event) {
            container.setOnClickListener {
                onClick(event)
            }
            if (event.id == "1") {
                imageView.setImageResource(R.drawable.img_event)
            } else {
                imageView.setImageResource(R.drawable.img_event_2)
            }
            title.text = event.title
            subtitle.text = event.subtitle

            date.text = getEventDateText(container, event.dates)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            onClick,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_event, parent, false)
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
