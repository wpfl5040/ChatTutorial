package com.wpfl5.chattutorial.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.wpfl5.chattutorial.model.request.MsgRequest
import com.wpfl5.chattutorial.model.request.User
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.model.response.MsgResponse
import com.wpfl5.chattutorial.model.response.RoomResponse
import com.wpfl5.chattutorial.model.response.UserResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    suspend fun setUserData(user: User) : Flow<FbResponse<Boolean>> = callbackFlow {
        db.collection("users")
            .document()
            .set(user)
            .addOnSuccessListener {
                offer(FbResponse.Success(true))
            }
            .addOnFailureListener { offer(FbResponse.Fail(it)) }

        awaitClose { this.cancel("StoreRepository-setUserData() : cancel") }
    }

    suspend fun getUserList(): Flow<FbResponse<List<UserResponse>?>>  = callbackFlow {
        db.collection("users")
            .get()
            .addOnSuccessListener {
                offer(FbResponse.Success(it.toObjects()))
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose { this.cancel("StoreRepository-getUserList() : cancel") }
    }

    suspend fun getRoomList(id: String): Flow<FbResponse<List<RoomResponse>?>> = callbackFlow {
        db.collection("rooms")
            .whereArrayContains("users", id)
            .get()
            .addOnSuccessListener {
                offer(FbResponse.Success(it.toObjects()))
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose { this.cancel("StoreRepository-getRoomList() : cancel") }
    }

    suspend fun getMsgList(rId: String): Flow<FbResponse<List<MsgResponse>?>> = callbackFlow {
        db.collection("rooms")
            .document(rId)
            .collection("messages")
            .orderBy("sentAt")
            .get()
            .addOnSuccessListener {
                offer(FbResponse.Success(it.toObjects()))
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose { this.cancel("StoreRepository-getMsgList() : cancel") }
    }

    suspend fun sendMsg(rId: String, msgRequest: MsgRequest) : Flow<FbResponse<Boolean>> = callbackFlow {
        db.collection("rooms")
            .document(rId)
            .collection("messages")
            .document()
            .set(msgRequest)
            .addOnSuccessListener {
                offer(FbResponse.Success(true))
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose { this.cancel("StoreRepository-sendMsg() : cancel") }


    }


}