package com.wpfl5.chattutorial.ui.main.friend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentMainFriendsBinding
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.adapter.FriendAdapter
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFriendsFragment : BaseVMFragment<FragmentMainFriendsBinding, FriendsViewModel>(){
    override fun getLayoutRes(): Int = R.layout.fragment_main_friends
    override val viewModel: FriendsViewModel by viewModels()
    private val mainVM: MainViewModel by activityViewModels()

    private val adapter = FriendAdapter {
        findNavController().navigate(
            MainFriendsFragmentDirections
                .actionFriendsFragmentToChatActivity(it)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            friendViewModel = viewModel
            mainViewModel = mainVM
            swipeRefresh.setOnRefreshListener { loadUserList() }
            recyclerFriend.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        loadUserList()
    }

    private fun loadUserList(){
        mainVM.userDataResponse.observing { result ->
            binding.result = result
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    if(!result.data.isNullOrEmpty()) {
                        adapter.submitList(result.data)
                    }
                }
                is FbResponse.Fail -> {
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }
}