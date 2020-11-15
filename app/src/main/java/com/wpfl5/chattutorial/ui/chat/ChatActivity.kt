package com.wpfl5.chattutorial.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityChatBinding
import com.wpfl5.chattutorial.ext.afterTextChanged
import com.wpfl5.chattutorial.ext.getString
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.adapter.ChatAdapter
import com.wpfl5.chattutorial.ui.base.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ChatActivity : BaseVMActivity<ActivityChatBinding, ChatViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_chat
    override val viewModel: ChatViewModel by viewModels()
    private val args : ChatActivityArgs by navArgs()
    @Inject lateinit var adapter : ChatAdapter
    private var isFirst: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            chatVM = viewModel
            imgbtnBack.setOnClickListener { finish() }
            friendName = args.friendId
            recyclerChat.adapter = adapter
            btnSend.setOnClickListener {
                val msg = inputText.getString()

                if(isFirst){
                    createRoomObserver(args.friendId, msg)
                }else{
                    viewModel.sendMsg(msg, args.friendId)
                }

                inputText.text.clear()

            }
        }

        getRoomData(args.friendId)
        textWatcher()
        sendMsgObserver()
        rIdObserver()
    }


    private fun getRoomData(friendId: String) {
        viewModel.getRoomData(friendId).observing(this) { result ->
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    if(result.data == null) {
                        isFirst = true
                        adapter.submitList(null)
                    } else{
                        //채팅 불러오기
                        viewModel.setRoomId(result.data.rid)
                        scrollToBottom()
                    }
                }
                is FbResponse.Fail -> { toast(result.e.message) }
            }
        }
    }

    private fun chatSnapshotObserver(){
        viewModel.chatSnapshot.observing(this) {result->
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    if(!result.data.isNullOrEmpty()) {
                        adapter.submitList(result.data)
                        scrollToBottom()
                    }
                }
                is FbResponse.Fail -> {
                    toast(result.e.message)
                }
            }
        }
    }

    private fun sendMsgObserver(){
        viewModel.sendChatDataResponse.observing(this) {result ->
            when(result){
                is FbResponse.Loading -> {

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

    private fun createRoomObserver(friendId: String, msg: String){
        viewModel.createRoom(friendId).observing(this) { result ->
            when(result){
                is FbResponse.Loading -> {

                }
                is FbResponse.Success -> {
                    isFirst = false
                    //val roomId = result.data
                    viewModel.setRoomId(result.data)
                    viewModel.sendMsg(msg, friendId)
                }
                is FbResponse.Fail -> {
                    toast(result.e.message)
                }
            }
        }
    }

    private fun rIdObserver(){
        viewModel.rId.observing(this) {
            chatSnapshotObserver()
        }
    }

    private fun scrollToBottom(){
        binding.recyclerChat.smoothScrollToPosition(adapter.itemCount)
    }

    private fun textWatcher(){
        with(binding){
            btnSend.isEnabled = false
            inputText.afterTextChanged {
                btnSend.isEnabled = !it.isBlank()
            }
        }
    }

}