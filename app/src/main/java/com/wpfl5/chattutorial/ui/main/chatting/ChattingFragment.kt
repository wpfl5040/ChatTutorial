package com.wpfl5.chattutorial.ui.main.chatting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentChattingBinding
import com.wpfl5.chattutorial.ext.afterTextChanged
import com.wpfl5.chattutorial.ext.getString
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.adapter.ChatAdapter
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChattingFragment : BaseVMFragment<FragmentChattingBinding, ChattingViewModel>(){
    override fun getLayoutRes(): Int = R.layout.fragment_chatting
    override val viewModel: ChattingViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args : ChattingFragmentArgs by navArgs()
    @Inject lateinit var adapter : ChatAdapter
    private var isFirst: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            chatVM = viewModel
            mainVM = mainViewModel
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getRoomData(args.friendId)
        textWatcher()
        sendMsgObserver()
        rIdObserver()
    }

    private fun getRoomData(friendId: String) {
        viewModel.getRoomData(friendId).observing(viewLifecycleOwner) { result ->
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    if(result.data == null) {
                        isFirst = true
                        adapter.submitList(null)
                    } else{
                        //채팅 불러오기
                        viewModel.setRoomId(result.data.rid)
                    }
                }
                is FbResponse.Fail -> { toast(requireContext(), result.e.message) }
            }
        }
    }

    private fun chatSnapshotObserver(){
        viewModel.chatSnapshot.observing(viewLifecycleOwner) {result->
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    if(!result.data.isNullOrEmpty()) {
                        adapter.submitList(result.data)
                        scrollToBottom()
                    }
                }
                is FbResponse.Fail -> {
                    toast(requireContext(), result.e.message)
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
                    toast(requireContext(), "success Sent")
                }
                is FbResponse.Fail -> {
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }

    private fun createRoomObserver(friendId: String, msg: String){
        viewModel.createRoom(friendId).observing(viewLifecycleOwner) { result ->
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
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }

    private fun rIdObserver(){
        viewModel.rId.observing(viewLifecycleOwner) {
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