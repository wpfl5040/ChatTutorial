package com.wpfl5.chattutorial.ui.main.friend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentMainFriendsBinding
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFriendsFragment : BaseVMFragment<FragmentMainFriendsBinding, FriendsViewModel>(){
    override fun getLayoutRes(): Int = R.layout.fragment_main_friends
    override val viewModel: FriendsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}