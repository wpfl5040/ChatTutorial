package com.wpfl5.chattutorial.ui.main.friend

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.wpfl5.chattutorial.model.Event
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.UserResponse
import com.wpfl5.chattutorial.repository.StorageRepository
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class FriendsViewModel @ViewModelInject constructor(
    storeRepository: StoreRepository,
    private val auth: FirebaseAuth,
    private val storageRepository: StorageRepository
) : BaseViewModel() {


    val userData: LiveData<FbResponse<UserResponse?>> = liveData(coroutineIoContext) {
        storeRepository.getUserData(auth.uid!!)
            .onStart { emit(FbResponse.Loading) }
            .collect { emit(it) }
    }

    fun saveImg(filePath: Uri) = liveData(coroutineIoContext) {
        storageRepository.putProfileImage(auth.uid!!, filePath)
            .onStart { emit(FbResponse.Loading) }
            .collect { emit(it) }
    }


    private val _showMyProfile = MutableLiveData<Event<Unit>>()
    val showMyProfile: LiveData<Event<Unit>> get() = _showMyProfile

    fun showMyProfileDialog() {
        _showMyProfile.value = Event(Unit)
    }


}