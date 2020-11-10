package com.wpfl5.chattutorial.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.DialogMyprofileBinding
import com.wpfl5.chattutorial.ext.afterTextChanged
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.UserResponse
import com.wpfl5.chattutorial.ui.base.BaseVMDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileDialog(
    private val user: UserResponse
) : BaseVMDialog<DialogMyprofileBinding, MyProfileDialogViewModel>() {
    override val layoutResId: Int = R.layout.dialog_myprofile
    override val viewModel: MyProfileDialogViewModel by viewModels()

    override fun DialogMyprofileBinding.onViewBound(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            currentName = user.name
            btnBack.setOnClickListener { dismiss() }
            editName.afterTextChanged {
                txtEditSize.text = it.length.toString()
                btnOk.isEnabled = it.isNotEmpty()
            }
            btnOk.setOnClickListener {
                updateObserver(editName.text.toString())
            }
        }
    }

    private fun updateObserver(name: String) {
        viewModel.updateUserData(
            fcmToken = user.fcmToken!!,
            id = user.id,
            name = name,
            uid = user.uid,
            profileImage = user.profileImage
        ).observing(viewLifecycleOwner) { result ->
            when (result) {
                is FbResponse.Loading -> { }
                is FbResponse.Success -> { dismiss() }
                is FbResponse.Fail -> { toast(requireContext(), result.e.message) }
            }
        }
    }
}