package com.wpfl5.chattutorial.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.RowRoomBinding
import com.wpfl5.chattutorial.model.response.RoomResponse
import com.wpfl5.chattutorial.ui.base.BaseAdapter

class RoomAdapter(
    val onItemClickListener: (RoomResponse) -> Unit
) : BaseAdapter<RoomResponse, RowRoomBinding>(
    object : DiffUtil.ItemCallback<RoomResponse>() {
        override fun areItemsTheSame(oldItem: RoomResponse, newItem: RoomResponse): Boolean {
            return oldItem.rid == newItem.rid
        }
        override fun areContentsTheSame(oldItem: RoomResponse, newItem: RoomResponse): Boolean {
            return oldItem == newItem
        }
    }
) {
    override val layoutRes: Int = R.layout.row_room
    override fun onBindViewHolder(binding: RowRoomBinding, item: RoomResponse) {
        binding.apply {
            room = item
            layoutConstraint.setOnClickListener { onItemClickListener(item) }
        }
    }
}