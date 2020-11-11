package com.wpfl5.chattutorial.ui.main.friend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentFriendProfileBinding
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendProfileFragment : BaseVMFragment<FragmentFriendProfileBinding, MainViewModel>(){
    override fun getLayoutRes(): Int = R.layout.fragment_friend_profile
    override val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mainViewModel = viewModel
            btnChat.setOnClickListener {  }
            btnPhone.setOnClickListener {  }
            btnZoom.setOnClickListener {  }
            btnClose.setOnClickListener { findNavController().navigateUp() }
        }
    }

    

}