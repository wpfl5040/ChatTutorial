package com.wpfl5.chattutorial.ui.main.room

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentMainRoomsBinding
import com.wpfl5.chattutorial.ext.getSpValue
import com.wpfl5.chattutorial.ext.gone
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.ext.visible
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.adapter.RoomAdapter
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainRoomsFragment : BaseVMFragment<FragmentMainRoomsBinding, RoomsViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_main_rooms
    override val viewModel: RoomsViewModel by viewModels()
    private val mainVM: MainViewModel by activityViewModels()

    private val adapter = RoomAdapter{

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            roomViewModel = viewModel
            mainViewModel = mainVM.apply { loadRoomData(requireContext().getSpValue("userId", "")) }
            swipeRefresh.setOnRefreshListener { loadRoom() }
            recyclerRoom.adapter = adapter
        }

        Log.d("//id", requireContext().getSpValue("userId", ""))
    }

    override fun onStart() {
        super.onStart()
        loadRoom()
    }

    private fun loadRoom(){
        mainVM.roomDataResponse.observing {result ->
            binding.result = result
            binding.txtExist.gone()
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    if(!result.data.isNullOrEmpty()) {
                        adapter.submitList(result.data)
                    }else{
                        binding.txtExist.visible()
                    }
                }
                is FbResponse.Fail -> {
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }
}