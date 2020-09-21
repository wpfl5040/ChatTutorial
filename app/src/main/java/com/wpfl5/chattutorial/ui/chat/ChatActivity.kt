package com.wpfl5.chattutorial.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityChatBinding
import com.wpfl5.chattutorial.ext.afterTextChanged
import com.wpfl5.chattutorial.ui.base.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : BaseVMActivity<ActivityChatBinding, ChatViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_chat
    override val viewModel: ChatViewModel by viewModels()
    private val args : ChatActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            chatViewModel = viewModel
            user = args.user


        }

        textWatcher()

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