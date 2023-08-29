package com.example.androidpractice.feature.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.transform.CircleCropTransformation
import com.example.androidpractice.core.designsystem.R
import com.example.androidpractice.core.ui.extensions.loadWithoutCaching
import com.example.androidpractice.feature.profile.databinding.ItemFriendBinding

internal class FriendsAdapter(private var friends: List<String>) :
    RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(private val binding: ItemFriendBinding) : ViewHolder(binding.root) {

        fun initViewHolder(name: String) {
            with(binding) {
                friendNameTextView.text = name
                avatarImageView.loadWithoutCaching("https://wanthelp-112222ed0ca0.herokuapp.com/categories/adult.png") {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_user_placeholder)
                    error(R.drawable.ic_user_placeholder)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFriends(friends: List<String>) {
        this.friends = friends
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendViewHolder {
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
