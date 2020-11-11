package com.wpfl5.chattutorial.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.RoomResponse
import com.wpfl5.chattutorial.model.response.UserResponse
import com.wpfl5.chattutorial.repository.StorageRepository
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.EventViewModelDelegate
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class MainViewModel @ViewModelInject constructor(
    private val storeRepository: StoreRepository,
    private val storageRepository: StorageRepository,
    delegate: EventViewModelDelegate
) : BaseViewModel(), EventViewModelDelegate by delegate {

    val userDataResponse: LiveData<FbResponse<List<UserResponse>?>>

    private var loadRoom: MutableLiveData<String> = MutableLiveData()
    val roomDataResponse: LiveData<FbResponse<List<RoomResponse>?>>


    init {
        userDataResponse = liveData(coroutineIoContext) {
            try {
                storeRepository.getUserList()
                    .onStart { emit(FbResponse.Loading) }
                    .collect {
                        emit(it)
                    }
            }catch (e: Exception){
                emit(FbResponse.Fail(e))
            }
        }

        roomDataResponse = loadRoom.switchMap {
            liveData(coroutineIoContext) {
                try {
                    storeRepository.getRoomList(it)
                        .onStart { emit(FbResponse.Loading) }
                        .collect { emit(it) }
                }catch (e: Exception) {
                    emit(FbResponse.Fail(e))
                }
            }
        }

    }

    fun updateUserData(fcmToken: String, id: String, name: String, uid: String, profileImage: String?) = liveData(coroutineIoContext) {
        storeRepository.updateUser(
            uid, fcmToken, id, name, profileImage
        )
            .onStart { emit(FbResponse.Loading) }
            .collect { emit(it) }
    }


    fun loadRoomData(id: String){
        loadRoom.value = id
    }



}