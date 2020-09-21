package com.wpfl5.chattutorial.model.response

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class RoomResponse(
    @DocumentId
    val rid: String,
    val name: String,
    val lastMessage: String,
    val createdBy: String,
    val users: List<String>?,
    @ServerTimestamp
    val createdAt: Timestamp?,
    @ServerTimestamp
    val modifiedAt: Timestamp?
) {
    constructor() : this("","","","",null,null, null)
}