package com.wpfl5.chattutorial.model.response

import com.google.firebase.firestore.DocumentId

data class UserResponse (
    @DocumentId
    val uid: String,
    val id: String,
    val name: String,
    val fcmToken: String?
) {
    constructor() : this("", "","","")
}