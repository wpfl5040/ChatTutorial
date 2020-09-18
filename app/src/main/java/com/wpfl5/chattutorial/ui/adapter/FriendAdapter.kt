package com.wpfl5.chattutorial.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.RowFriendBinding
import com.wpfl5.chattutorial.model.response.UserResponse
import com.wpfl5.chattutorial.ui.base.BaseAdapter

class FriendAdapter(
    val onItemClickListener: (UserResponse) -> Unit
) : BaseAdapter<UserResponse, RowFriendBinding>(
    object : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem == newItem
        }
    }
) {
    override val layoutRes: Int = R.layout.row_friend
    override fun onBindViewHolder(binding: RowFriendBinding, item: UserResponse) {
        binding.apply {
            user = item
            layoutLinear.setOnClickListener { onItemClickListener(item) }
        }

    }
}