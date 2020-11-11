package com.wpfl5.chattutorial.ui.main.friend

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.FragmentMyProfileBinding
import com.wpfl5.chattutorial.ext.toast
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.UserResponse
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.dialog.MyProfileDialog
import com.wpfl5.chattutorial.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyProfileFragment : BaseVMFragment<FragmentMyProfileBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_my_profile
    override val viewModel: MainViewModel by activityViewModels()
    private val friendViewModel: FriendsViewModel by viewModels()
    @Inject lateinit var storage: FirebaseStorage

    companion object{
        private const val REQUEST_GALLERY_PROFILE_CODE = 100
        private const val REQUEST_GALLERY_BACKGROUND_CODE = 101
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mainViewModel = viewModel
            btnClose.setOnClickListener { findNavController().navigateUp() }
            btnEditBackground.setOnClickListener { goToGallery(1) }
            btnEditProfile.setOnClickListener { friendViewModel.showMyProfileDialog() }
            btnEditProfileImage.setOnClickListener { goToGallery(0) }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userObserver()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null && resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_GALLERY_PROFILE_CODE){
                val filePath = data.data
                if(filePath != null) {
                    // save Image
                    saveImgObserver(filePath)
                    Log.d("//filePath - profile", filePath.toString())
                }
            }else if(requestCode == REQUEST_GALLERY_BACKGROUND_CODE) {
                val filePath = data.data
                if(filePath != null) {
                    // save Image
                    saveImgObserver(filePath)
                    Log.d("//filePath- background", filePath.toString())
                }
            }
        }
    }

    private fun goToGallery(separator: Int){
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        if(separator == 0){
            //프로필 사진 선택
            startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), REQUEST_GALLERY_PROFILE_CODE)
        }else{
            // 배경 사진 선택
            startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), REQUEST_GALLERY_BACKGROUND_CODE)
        }
    }


    private fun saveImgObserver(uri: Uri){
        friendViewModel.saveImg(uri).observing(viewLifecycleOwner) { result ->
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    //firestore update 부분
                    toast(requireContext(), "성공")
                }
                is FbResponse.Fail -> {
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }

    private fun myProfileDialogObserver(user: UserResponse) {
        friendViewModel.showMyProfile.eventObserving(viewLifecycleOwner) {
            MyProfileDialog(user).show(parentFragmentManager, "MyProfileDialog")
        }
    }

    private fun userObserver() {
        friendViewModel.userData.observing(viewLifecycleOwner) { result ->
            when (result) {
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    val user = result.data!!
                    user.profile = storage.reference.child("profileImage/${user.profileImage}")
                    binding.user = user
                    Log.d("//user", user.toString())
                    myProfileDialogObserver(user)
                }
                is FbResponse.Fail -> {
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }

}