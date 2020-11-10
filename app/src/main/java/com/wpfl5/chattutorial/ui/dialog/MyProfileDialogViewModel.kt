package com.wpfl5.chattutorial.ui.dialog

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class MyProfileDialogViewModel @ViewModelInject constructor(
    private val storeRepository: StoreRepository
) : BaseViewModel() {

    fun updateUserData(fcmToken: String, id: String, name: String, uid: String, profileImage: String?) = liveData(coroutineIoContext) {
        storeRepository.updateUser(
            uid, fcmToken, id, name, profileImage
        )
            .onStart { emit(FbResponse.Loading) }
            .collect { emit(it) }
    }

}