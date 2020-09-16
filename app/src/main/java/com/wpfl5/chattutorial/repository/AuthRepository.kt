package com.wpfl5.chattutorial.repository

import com.google.firebase.auth.FirebaseAuth
import com.wpfl5.chattutorial.model.FbResponse
import com.wpfl5.chattutorial.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    suspend fun login(user: User) : Flow<FbResponse<Boolean>> = callbackFlow {
        auth.signInWithEmailAndPassword(user.id, user.password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    offer(FbResponse.Success(true))
                }
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose { this.cancel("AuthRepository : Login Cancel") }
    }

    suspend fun register(user: User) : Flow<FbResponse<Boolean>> = callbackFlow {
        auth.createUserWithEmailAndPassword(user.id, user.password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    offer(FbResponse.Success(true))
                }
            }
            .addOnFailureListener {
                offer(FbResponse.Fail(it))
            }

        awaitClose { this.cancel("AuthRepository : Register Cancel") }

    }
}