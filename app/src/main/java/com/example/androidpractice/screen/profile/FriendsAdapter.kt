package com.example.androidpractice.screen.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidpractice.R
import com.example.androidpractice.databinding.ItemFriendBinding

class FriendsAdapter(private var friends: List<String>) :
    RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(private val binding: ItemFriendBinding) : ViewHolder(binding.root) {

        fun initViewHolder(name: String) {
            with(binding) {
                friendNameTextView.text = name
                avatarImageView.setImageResource(R.drawable.ic_friend)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFriends(friends: List<String>) {
        this.friends = friends
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.initViewHolder(friends[position])
    }

    override fun getItemCount() = friends.size
}
