package com.wpfl5.chattutorial.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.wpfl5.chattutorial.model.FbResponse
import com.wpfl5.chattutorial.model.User
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

}