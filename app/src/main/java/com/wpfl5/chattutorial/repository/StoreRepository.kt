package com.wpfl5.chattutorial.repository

import android.util.Log
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
    db: FirebaseFirestore
) {

    private val usersCollection = db.collection("users")
    private val roomCollection = db.collection("rooms")

    suspend fun setUserData(user: User) : Flow<FbResponse<Boolean>> = callbackFlow {
        usersCollection
            .document()
            .set(user)
            .addOnSuccessListener {
                offer(FbResponse.Success(true))
            }
            .addOnFailureListener { offer(FbResponse.Fail(it)) }

        awaitClose { this.cancel("StoreRepository-setUserData() : cancel") }
    }

    suspend fun getUserList(): Flow<FbResponse<List<UserResponse>?>>  = callbackFlow {
        usersCollection
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
        roomCollection
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
        roomCollection
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
        roomCollection
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


    suspend fun chatSnapshot(rId: String): Flow<FbResponse<List<MsgResponse>?>> = callbackFlow {
        val query = roomCollection.document(rId).collection("messages")

        val snap = query.addSnapshotListener { snapshots, error ->
            if(error != null){
                Log.e("//repository", "listen: error ",error)
                offer(FbResponse.Fail(error))
                return@addSnapshotListener
            }else{
                val responseData = snapshots!!.toObjects<MsgResponse>()
                offer(FbResponse.Success(responseData.sortedBy { it.sentAt }))
//                val responseData = mutableListOf<MsgResponse>()
//                Log.d("//testt", test.toString())
//                for(dc in snapshots!!.documentChanges){
//                    val data = dc.document.data
//
//                    val msg: String = data["msg"] as String
//                    val receiveBy: String = data["receiveBy"] as String
//                    val sentBy: String = data["sentBy"] as String
//                    val sentAt: Timestamp = data["sentAt"] as Timestamp
//
//
//                    responseData.add(MsgResponse(
//                        msg, receiveBy, sentBy, sentAt
//                    ))
//
//                    if(dc.type == DocumentChange.Type.ADDED){
//                        Log.d("//repository", "New Data : ${dc.document.data}")
//                    }
//                }

            }

        }

        awaitClose { snap.remove() }
    }


    suspend fun updateRoom(rId: String): Flow<FbResponse<Boolean>> = callbackFlow {
        roomCollection.document(rId)

    }

}