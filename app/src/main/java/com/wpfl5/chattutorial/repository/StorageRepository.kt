package com.wpfl5.chattutorial.repository

import com.google.firebase.storage.FirebaseStorage
import com.wpfl5.chattutorial.model.response.FbResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: FirebaseStorage
) {

    suspend fun getProfileImage(imgResource: String) = callbackFlow {
        val ref = storage.reference.child("profileImage/$imgResource")

        offer(FbResponse.Success(ref))

//        ref.downloadUrl.addOnSuccessListener {
//            offer(FbResponse.Success(it))
//        }.addOnFailureListener {
//            offer(FbResponse.Fail(it))
//        }

        awaitClose { this.cancel() }
    }


}