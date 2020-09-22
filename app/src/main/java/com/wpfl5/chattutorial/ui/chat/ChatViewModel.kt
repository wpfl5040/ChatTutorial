package com.wpfl5.chattutorial.ui.chat

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.MsgResponse
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect

class ChatViewModel @ViewModelInject constructor(
    val repository: StoreRepository
) : BaseViewModel() {

    private val _chatDataRequest = MutableLiveData<String>()
    val chatResponse: LiveData<FbResponse<List<MsgResponse>?>>

    init {
        chatResponse = _chatDataRequest.switchMap {
            liveData(coroutineIoContext) {
                emit(FbResponse.Loading)
                try {
                    repository.getMsgList(it).collect {
                        emit(it)
                    }
                } catch (e: Exception) {
                    emit(FbResponse.Fail(e))
                }
            }
        }
    }


    fun setChatDataRequest(id: String){
        _chatDataRequest.value = id
    }
}