package com.wpfl5.chattutorial.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityChatBinding
import com.wpfl5.chattutorial.ui.base.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : BaseVMActivity<ActivityChatBinding, ChatViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_chat
    override val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

        }
    }
}