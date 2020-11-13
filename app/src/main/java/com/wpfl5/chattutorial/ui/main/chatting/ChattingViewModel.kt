package com.wpfl5.chattutorial.ui.main.chatting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.google.firebase.auth.FirebaseAuth
import com.wpfl5.chattutorial.model.request.MsgRequest
import com.wpfl5.chattutorial.model.request.RoomRequest
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.MsgResponse
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class ChattingViewModel @ViewModelInject constructor(
    private val storeRepository: StoreRepository,
    private val auth: FirebaseAuth
) : BaseViewModel() {

    private val _rId: MutableLiveData<String> = MutableLiveData()
    val rId: LiveData<String> get() = _rId


    fun getRoomData(friendId: String) = liveData(coroutineIoContext) {
        storeRepository.getRoomData(
            myId = auth.currentUser!!.email!!,
            friendId = friendId
        )
            .onStart { emit(FbResponse.Loading) }
            .collect { emit(it) }
    }

    val chatSnapshot : LiveData<FbResponse<List<MsgResponse>?>> = rId.switchMap {
        liveData(coroutineIoContext) {
            try {
                storeRepository.chatSnapshot(it)
                    .onStart { emit(FbResponse.Loading) }
                    .collect { emit(it) }
            } catch (e: Exception) {
                emit(FbResponse.Fail(e))
            }
        }
    }


    private val _sendChatData = MutableLiveData<MsgRequest>()
    val sendChatDataResponse : LiveData<FbResponse<Boolean>> = _sendChatData.switchMap {
        liveData(coroutineIoContext) {
            try {
                storeRepository.sendMsg(rId.value!!, it)
                    .onStart { emit(FbResponse.Loading) }
                    .collect { emit(it) }
            } catch (e: Exception) {
                emit(FbResponse.Fail(e))
            }
        }
    }

    fun sendMsg(msg: String, friendId: String){
        _sendChatData.value = MsgRequest(
            msg = msg,
            receiveBy = friendId,
            sentBy = auth.currentUser!!.email!!
        )
    }

    fun createRoom(friendId: String) = liveData(coroutineIoContext) {
        val roomRequest = RoomRequest(
            createdBy = auth.currentUser!!.email!!,
            users = listOf(auth.currentUser!!.email!!, friendId)
        )
        try {
            storeRepository.createRoom(roomRequest)
                .onStart { emit(FbResponse.Loading) }
                .collect { emit(it) }
        }catch (e: Exception){
            emit(FbResponse.Fail(e))
        }
    }

    fun setRoomId(rId: String) {
        _rId.value = rId
    }

//    private val _sendChatData = MutableLiveData<MsgRequest>()
//    val sendChatDataResponse : LiveData<FbResponse<Boolean>> = _sendChatData.switchMap {
//        liveData(coroutineIoContext) {
//            emit(FbResponse.Loading)
//            try {
//                storeRepository.sendMsg(_roomData.value!!.rid, it).collect { emit(it) }
//            } catch (e: Exception) {
//                emit(FbResponse.Fail(e))
//            }
//        }
//    }



}