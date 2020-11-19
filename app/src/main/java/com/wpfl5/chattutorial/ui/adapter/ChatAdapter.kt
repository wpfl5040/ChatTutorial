package com.wpfl5.chattutorial.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.wpfl5.chattutorial.BR
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.RowChatFriendBinding
import com.wpfl5.chattutorial.databinding.RowChatMeBinding
import com.wpfl5.chattutorial.ext.getSpValue
import com.wpfl5.chattutorial.model.response.MsgResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatAdapter @Inject constructor(
    @ApplicationContext val context: Context
) : ListAdapter<MsgResponse, RecyclerView.ViewHolder>(DiffObj){

    private var date = ""

    companion object DiffObj: DiffUtil.ItemCallback<MsgResponse>(){
        override fun areContentsTheSame(oldItem: MsgResponse, newItem: MsgResponse): Boolean {
            return oldItem.mId == newItem.mId
        }

        override fun areItemsTheSame(oldItem: MsgResponse, newItem: MsgResponse): Boolean {
            return oldItem == newItem
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            R.layout.row_chat_me -> {
                val msg = getItem(position)
                (holder as ChatViewHolder<*>).bind(msg)
            }
            R.layout.row_chat_friend -> {
                val msg = getItem(position)
                (holder as ChatViewHolder<*>).bind(msg)
            }
        }

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


    private fun timeStampToString(timestamp: Timestamp) : String {
        val mill = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val netDate = Date(mill)
        return sdf.format(netDate).toString()
    }




    inner class ChatViewHolder<T: ViewDataBinding> constructor(val binding: T) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MsgResponse){
            binding.apply {

                if(date.isEmpty()){
                    date = timeStampToString(item.sentAt!!)
                    item.isDateTime = true
                    Log.d("//testData", date)
                }else {
                    //날짜 비교 같으면 false 다르면 true

                    if (date == timeStampToString(item.sentAt!!)) {
                        item.isDateTime = false
                    } else {
                        date = timeStampToString(item.sentAt)
                        item.isDateTime = true
                    }

                }
                setVariable(BR.msg, item)
                executePendingBindings()
            }
        }
    }





}


