package com.wpfl5.chattutorial.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.DialogMyprofileBinding
import com.wpfl5.chattutorial.ext.afterTextChanged
import com.wpfl5.chattutorial.ui.base.BaseVMDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileDialog(
    private val name: String?
) : BaseVMDialog<DialogMyprofileBinding, MyProfileDialogViewModel>() {
    override val layoutResId: Int = R.layout.dialog_myprofile
    override val viewModel: MyProfileDialogViewModel by viewModels()

    override fun DialogMyprofileBinding.onViewBound(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            vm = viewModel
            currentName = name
            btnBack.setOnClickListener { dismiss() }
            editName.afterTextChanged {
                txtEditSize.text = it.length.toString()
                btnOk.isEnabled = it.isNotEmpty()
            }
            btnOk.setOnClickListener {
                val modiName = editName.text.toString()

                dismiss()
            }
        }
    }
}