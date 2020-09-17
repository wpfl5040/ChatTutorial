package com.wpfl5.chattutorial.ui.main.room

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentMainRoomsBinding
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainRoomsFragment : BaseVMFragment<FragmentMainRoomsBinding, RoomsViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_main_rooms
    override val viewModel: RoomsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}