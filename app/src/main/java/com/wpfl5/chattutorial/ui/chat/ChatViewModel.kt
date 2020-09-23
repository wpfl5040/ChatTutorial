package com.wpfl5.chattutorial.ui.chat

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.wpfl5.chattutorial.model.request.MsgRequest
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.MsgResponse
import com.wpfl5.chattutorial.model.response.RoomResponse
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect

class ChatViewModel @ViewModelInject constructor(
    val repository: StoreRepository
) : BaseViewModel() {

    private val _roomData = MutableLiveData<RoomResponse>()

    private val _sendChatData = MutableLiveData<MsgRequest>()
    val sendChatDataResponse : LiveData<FbResponse<Boolean>> = _sendChatData.switchMap {
        liveData(coroutineIoContext) {
            emit(FbResponse.Loading)
            try {
                repository.sendMsg(_roomData.value!!.rid, it).collect { emit(it) }
            } catch (e: Exception) {
                emit(FbResponse.Fail(e))
            }
        }
    }

    val chatResponse: LiveData<FbResponse<List<MsgResponse>?>>

    init {
        chatResponse = _roomData.switchMap {
            liveData(coroutineIoContext) {
                emit(FbResponse.Loading)
                try {
                    repository.getMsgList(it.rid).collect {
                        emit(it)
                    }
                } catch (e: Exception) {
                    emit(FbResponse.Fail(e))
                }
            }
        }
    }


    fun sendMsg(msgRequest: MsgRequest){
        _sendChatData.value = msgRequest
    }

    fun setRoomData(room: RoomResponse){
        _roomData.value = room
    }
}