package com.example.androidpractice.screen.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.R

class FriendsAdapter(private var friends: List<String>) :
    RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(view: View) : ViewHolder(view) {
        private val textView: TextView
        private val imageView: ImageView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.friendNameTextView)
            imageView = view.findViewById(R.id.avatarImageView)
        }

        fun initViewHolder(name: String) {
            textView.text = name
            imageView.setImageResource(R.drawable.ic_friend)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFriends(friends: List<String>) {
        this.friends = friends
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend, parent, false)

        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.initViewHolder("Дмитрий Валерьевич")
    }

    override fun getItemCount() = friends.size
}
