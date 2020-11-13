package com.wpfl5.chattutorial.repository

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.wpfl5.chattutorial.model.request.MsgRequest
import com.wpfl5.chattutorial.model.request.RoomRequest
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
            .document(user.uid)
            .set(user)
            .addOnSuccessListener {
                offer(FbResponse.Success(true))
            }
            .addOnFailureListener { offer(FbResponse.Fail(it)) }

        awaitClose { this.cancel("StoreRepository-setUserData() : cancel") }
    }

    suspend fun getUserData(uid: String) : Flow<FbResponse<UserResponse?>> = callbackFlow {
        val registration = usersCollection.document(uid).addSnapshotListener { snapshots, e ->
            if(e != null){
                offer(FbResponse.Fail(e))
                return@addSnapshotListener
            }

            if (snapshots != null && snapshots.exists()) {
                val user = snapshots.toObject<UserResponse>()
                Log.d("//StoreRepository", "Current data: $user")
                offer(FbResponse.Success(user))
            } else {
                Log.d("//StoreRepository", "Current data: null")
            }
        }
        awaitClose { registration.remove() }
    }

    suspend fun updateUser(uid: String, fcmToken: String, id: String, name: String, profileImage: String?) = callbackFlow {
        usersCollection.document(uid).update(mapOf(
            "fcmToken" to fcmToken,
            "id" to id,
            "name" to name,
            "uid" to uid,
            "profileImage" to profileImage
        ))
            .addOnSuccessListener {
                offer(FbResponse.Success(true))
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose{ this.cancel() }
    }

    suspend fun getUserList(): Flow<FbResponse<List<UserResponse>?>>  = callbackFlow {
        val userList = mutableListOf<UserResponse>()

        val registration = usersCollection.addSnapshotListener { snapshots, e ->
            if(e != null){
                offer(FbResponse.Fail(e))
                return@addSnapshotListener
            }


            for(dc in snapshots!!.documentChanges){
                when(dc.type){
                    DocumentChange.Type.ADDED -> {
                        val data = dc.document.toObject<UserResponse>()
                        userList.add(data)
                        Log.d("//StoreRepository", "New data: ${data.toString()}")
                    }
                    DocumentChange.Type.MODIFIED -> {
                        Log.d("//이전 데이터", userList.toString())
                        //수정시 리스트에서 아이디 삭제 -> 변경된 데이터 추가
                        val data = dc.document.toObject<UserResponse>()
                        userList.removeIf { it.id == data.id }
                        userList.add(data)
                        Log.d("//StoreRepository", "Modified data: $data")
                    }
                    DocumentChange.Type.REMOVED -> {
                        userList.removeIf {
                            it.id == dc.document.data["id"]
                        }
                        Log.d("//StoreRepository", "Removed data: ${dc.document.data}")
                    }
                }
            }

            offer(FbResponse.Success(userList))

        }

        awaitClose { registration.remove() }
    }


    suspend fun getRoomList(id: String) : Flow<FbResponse<List<RoomResponse>?>> = callbackFlow {
        val query = roomCollection.whereArrayContains("users", id)
        val roomList = mutableListOf<RoomResponse>()


        val registration = query.addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("//StoreRepository", "listen:error", e)
                    offer(FbResponse.Fail(e))
                    return@addSnapshotListener
                }


                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            val data = dc.document.toObject<RoomResponse>()
                            roomList.add(data)
                            Log.d("//StoreRepository", "New city: ${dc.document.data}")
                        }
                        DocumentChange.Type.MODIFIED -> {
                            Log.d("//StoreRepository", "Modified city: ${dc.document.data}")
                        }
                        DocumentChange.Type.REMOVED -> {
                            Log.d("//StoreRepository", "Removed city: ${dc.document.data}")
                        }
                    }
                }
                offer(FbResponse.Success(roomList))
            }

        awaitClose { registration.remove() }
    }

    suspend fun getRoomData(myId: String, friendId: String) = callbackFlow {
        roomCollection
            .whereArrayContains("users", friendId)
            .get()
            .addOnSuccessListener {
                var responseData: RoomResponse? = null

                val roomList = it.toObjects<RoomResponse>()
                roomList.forEach {room ->
                    if(room.users.contains(myId)){
                        //채팅이 있는 경우
                        responseData = room
                        return@forEach
                    }
                }

                offer(FbResponse.Success(responseData))
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose { this.cancel() }
    }

    suspend fun createRoom(roomRequest: RoomRequest) = callbackFlow {
        roomCollection.add(roomRequest)
            .addOnSuccessListener {
                offer(FbResponse.Success(it.id))
            }
            .addOnFailureListener { FbResponse.Fail(it) }

        awaitClose { this.cancel() }
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

        val registration = query.addSnapshotListener { snapshots, error ->
            if(error != null){
                Log.e("//repository", "listen: error ",error)
                offer(FbResponse.Fail(error))
                return@addSnapshotListener
            }else{
                val responseData = snapshots!!.toObjects<MsgResponse>()
                offer(FbResponse.Success(responseData.sortedBy { it.sentAt }))
            }

        }

        awaitClose { registration.remove() }
    }



}