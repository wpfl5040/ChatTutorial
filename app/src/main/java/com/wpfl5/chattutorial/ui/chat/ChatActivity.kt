package com.wpfl5.chattutorial.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityChatBinding
import com.wpfl5.chattutorial.ext.afterTextChanged
import com.wpfl5.chattutorial.ext.getSpValue
import com.wpfl5.chattutorial.ext.getString
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.request.MsgRequest
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.adapter.ChatAdapter
import com.wpfl5.chattutorial.ui.base.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : BaseVMActivity<ActivityChatBinding, ChatViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_chat
    override val viewModel: ChatViewModel by viewModels()
    private val args : ChatActivityArgs by navArgs()

    private lateinit var adapter : ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ChatAdapter(applicationContext)
        binding.apply {
            chatViewModel = viewModel.apply { setRoomData(args.room) }
            room = args.room
            recyclerChat.adapter = adapter

            inputText.setOnClickListener { scrollToBottom() }

            btnSend.setOnClickListener {
                val msg = inputText.getString()
                val sentId = getSpValue("userId","")
                val receiveId = args.room.users?.filter { it!=sentId }!![0]
                val msgRequest = MsgRequest(msg, receiveId, sentId)
                viewModel.sendMsg(msgRequest)
                inputText.text.clear()
            }

        }

    }

    override fun onStart() {
        super.onStart()
        //loadChatData()
        textWatcher()
        sendMsgObserver()
        chatSnapshotObserver()
    }



    private fun textWatcher(){
        with(binding){
            btnSend.isEnabled = false
            inputText.afterTextChanged {
                btnSend.isEnabled = !it.isBlank()
            }
        }
    }

//    private fun loadChatData(){
//        viewModel.chatResponse.observing {result ->
//            when(result){
//                is FbResponse.Loading -> { }
//                is FbResponse.Success -> {
//                    if(!result.data.isNullOrEmpty()) {
//                        adapter.submitList(result.data)
//                    }
//                }
//                is FbResponse.Fail -> {
//                    toast(result.e.message)
//                }
//            }
//        }
//    }

    private fun sendMsgObserver(){
        viewModel.sendChatDataResponse.observing{result ->
            when(result){
                is FbResponse.Loading -> {
                    val newList = mutableListOf<MsgRequest>()
                    scrollToBottom()
                }
                is FbResponse.Success -> {
                    toast("success Sent")
                }
                is FbResponse.Fail -> {
                    toast(result.e.message)
                }
            }
        }
    }

    private fun scrollToBottom(){
        binding.recyclerChat.layoutManager?.scrollToPosition(adapter.itemCount-1)
    }

    private fun chatSnapshotObserver(){
        viewModel.chatSnapshot.observing {result->
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    if(!result.data.isNullOrEmpty()) {
                        adapter.submitList(result.data)
                    }
                }
                is FbResponse.Fail -> {
                    toast(result.e.message)
                }
            }
        }
    }


}