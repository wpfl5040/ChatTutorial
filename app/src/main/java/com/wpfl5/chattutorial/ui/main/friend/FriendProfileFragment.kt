package com.wpfl5.chattutorial.ui.main.friend

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentFriendProfileBinding
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendProfileFragment : BaseVMFragment<FragmentFriendProfileBinding, MainViewModel>(){
    override fun getLayoutRes(): Int = R.layout.fragment_friend_profile
    override val viewModel: MainViewModel by activityViewModels()
    private val args : FriendProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friend = args.user

        binding.apply {
            mainViewModel = viewModel
            this.friend = friend
            btnChat.setOnClickListener { findNavController().navigate(FriendProfileFragmentDirections.actionFriendProfileFragmentToChattingFragment(friend.id)) }
            btnPhone.setOnClickListener { startActivity(Intent(ACTION_VIEW, Uri.parse("tel:"))) }
            btnZoom.setOnClickListener {  }

        }
    }


//
//    private fun findRoom(friendId: String){
//        viewModel.roomDataResponse.observing(viewLifecycleOwner) {result ->
//            when(result) {
//                is FbResponse.Loading -> { }
//                is FbResponse.Success -> {
//                    val rooms = result.data
//                    Log.d("//room", rooms.toString())
//                    if(rooms.isNullOrEmpty()){
//                        //채팅이 없는 경우
//
//                    }else{
//                        //채팅이 있는 경우 -> 친구와 채팅이 있는지 확인
//                        var friendRoom: RoomResponse? = null
//                        rooms.forEach {room ->
//                            if(room.users.contains(friendId)){
//                                friendRoom = room
//                                return@forEach
//                            }
//                        }
//
//                        if(friendRoom != null){
//                            //채팅이 존재 하는 경우
//                            //findNavController().navigate(FriendProfileFragmentDirections.actionFriendProfileFragmentToChatActivity(friendRoom!!))
//                            findNavController().navigate(FriendProfileFragmentDirections.actionFriendProfileFragmentToChattingFragment())
//                        }else{
//                            //채팅이 존재하지 않는 경우
////                            val roomdata = RoomResponse("","","","", listOf(),null, null)
//                            findNavController().navigate(FriendProfileFragmentDirections.actionFriendProfileFragmentToChattingFragment())
//                        }
//
//                    }
//
//                }
//                is FbResponse.Fail -> {
//                    toast(requireContext(), result.e.message)
//                }
//            }
//        }
//    }

//    private fun findRoom(friendId: String){
//        viewModel.findRoomData(friendId).observing {
//            when(it){
//                is FbResponse.Loading -> { }
//                is FbResponse.Success -> {
//                    val room = it.data
//                    Log.d("//room", room.toString())
//                    if(room == null){
//                        //채팅이 없는 경우
//
//                    }else{
//                        //채팅이 있는 경우
//
//                    }
//
//
//                }
//                is FbResponse.Fail -> {
//                    toast(requireContext(), it.e.message)
//                }
//            }
//        }
//    }


}