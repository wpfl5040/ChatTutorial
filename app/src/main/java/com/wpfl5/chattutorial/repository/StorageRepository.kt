package com.wpfl5.chattutorial.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.wpfl5.chattutorial.model.response.FbResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: FirebaseStorage
) {

    suspend fun putProfileImage(filePath: Uri) = callbackFlow {
        val ref = storage.getReferenceFromUrl("gs://chattutorial-aeedc.appspot.com")

        val currentDateTime =
            SimpleDateFormat("MM월-dd일-HH:mm:ss", Locale.KOREA).format(System.currentTimeMillis())

        val fileName = "$currentDateTime"

        ref.child("profileImage/")
            .child(fileName)
            .putFile(filePath)
            .addOnSuccessListener { offer(FbResponse.Success(true)) }
            .addOnFailureListener { offer(FbResponse.Fail(it)) }


        awaitClose { this.cancel() }
    }


}