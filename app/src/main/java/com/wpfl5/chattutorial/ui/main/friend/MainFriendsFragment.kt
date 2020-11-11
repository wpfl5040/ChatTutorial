package com.wpfl5.chattutorial.ui.main.friend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentMainFriendsBinding
import com.wpfl5.chattutorial.ext.getSpValue
import com.wpfl5.chattutorial.ext.gone
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.adapter.FriendAdapter
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFriendsFragment : BaseVMFragment<FragmentMainFriendsBinding, FriendsViewModel>(){
    override fun getLayoutRes(): Int = R.layout.fragment_main_friends
    override val viewModel: FriendsViewModel by viewModels()
    private val mainVM: MainViewModel by activityViewModels()
    @Inject lateinit var storage: FirebaseStorage
    lateinit var name: String

    private val adapter = FriendAdapter { }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = requireContext().getSpValue("userId", "")
        binding.apply {
            friendViewModel = viewModel
            mainViewModel = mainVM
            lytMyInfo.setOnClickListener {
                findNavController().navigate(R.id.action_friendsFragment_to_myProfileFragment)
            }
            recyclerFriend.adapter = adapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadUserList()
    }

    private fun loadUserList(){
        mainVM.userDataResponse.observing(viewLifecycleOwner) { result ->
            binding.result = result
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    binding.progressFriend.gone()
                    if(!result.data.isNullOrEmpty()) {
                        val responseData = result.data.toMutableList()
                        responseData.forEach {user ->
                            user.profile = storage.reference.child("profileImage/${user.profileImage}")
                        }
                        binding.user = responseData.first { it.id == name }
                        responseData.removeIf {user -> user.id == name }
                        adapter.submitList(responseData.toList())
                    }
                }
                is FbResponse.Fail -> { toast(requireContext(), result.e.message) }
            }
        }
    }




}