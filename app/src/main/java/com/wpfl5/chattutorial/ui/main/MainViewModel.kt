package com.wpfl5.chattutorial.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.UserResponse
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.EventViewModelDelegate
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect

class MainViewModel @ViewModelInject constructor(
    storeRepository: StoreRepository,
    delegate: EventViewModelDelegate
) : BaseViewModel(), EventViewModelDelegate by delegate {

    val userDataResponse: LiveData<FbResponse<List<UserResponse>?>>


    init {
        userDataResponse = liveData(coroutineIoContext) {
            emit(FbResponse.Loading)
            try {
                storeRepository.getUserList().collect {
                    emit(it)
                }
            }catch (e: Exception){
                emit(FbResponse.Fail(e))
            }
        }
    }


}