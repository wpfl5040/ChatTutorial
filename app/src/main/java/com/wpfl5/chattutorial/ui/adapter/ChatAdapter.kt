package com.wpfl5.chattutorial.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wpfl5.chattutorial.BR
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.RowChatFriendBinding
import com.wpfl5.chattutorial.databinding.RowChatMeBinding
import com.wpfl5.chattutorial.ext.getSpValue
import com.wpfl5.chattutorial.model.response.MsgResponse

class ChatAdapter constructor(val context: Context) : ListAdapter<MsgResponse, RecyclerView.ViewHolder>(diffCallback) {
    companion object{

        object diffCallback: DiffUtil.ItemCallback<MsgResponse>(){
            override fun areContentsTheSame(oldItem: MsgResponse, newItem: MsgResponse): Boolean {
                return oldItem.mId == newItem.mId
            }

            override fun areItemsTheSame(oldItem: MsgResponse, newItem: MsgResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = getItem(position)
        (holder as ChatViewHolder<*>).bind(msg)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when(viewType) {
            R.layout.row_chat_me -> ChatViewHolder<RowChatMeBinding>(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_chat_me,
                        parent,
                        false
                    )
                )
            R.layout.row_chat_friend -> ChatViewHolder<RowChatFriendBinding>(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_chat_friend,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }



    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val myId = context.getSpValue("userId", "")
        return if(item.sentBy == myId) R.layout.row_chat_me
                else R.layout.row_chat_friend
    }


    class ChatViewHolder<T: ViewDataBinding> constructor(val binding: T) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MsgResponse){
            binding.apply {
                setVariable(BR.msg, item)
                executePendingBindings()
            }
        }

    }




}


