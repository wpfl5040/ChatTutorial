package com.wpfl5.chattutorial.ui.main.friend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentMyProfileBinding
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.dialog.MyProfileDialog
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseVMFragment<FragmentMyProfileBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_my_profile
    override val viewModel: MainViewModel by activityViewModels()
    private val friendViewModel: FriendsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mainViewModel = viewModel
            btnClose.setOnClickListener { findNavController().navigateUp() }
            btnEditBackground.setOnClickListener { }
            btnEditProfile.setOnClickListener { friendViewModel.showMyProfileDialog() }
            btnEditProfileImage.setOnClickListener { }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userObserver()
    }


    private fun myProfileDialogObserver(name: String?) {
        friendViewModel.showMyProfile.eventObserving(viewLifecycleOwner) {
            MyProfileDialog(name).show(parentFragmentManager, "MyProfileDialog")
        }
    }

    private fun userObserver() {
        friendViewModel.userData.observing(viewLifecycleOwner) { result ->
            when (result) {
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    binding.user = result.data
                    myProfileDialogObserver(result.data?.name)
                }
                is FbResponse.Fail -> {
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }

}