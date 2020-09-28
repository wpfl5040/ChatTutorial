package com.wpfl5.chattutorial.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

data class RoomRequest(
    val lastMessage: String,
    val lastMessageBy: String,
    val lastMessageCount: Int,
    val lastMessageRead: Boolean,
    val modifiedAt: Timestamp?
) {

    constructor(lastMessage: String, lastMessageBy: String): this(lastMessage, lastMessageBy,0,true, Timestamp.now())

    fun getMapData() : Map<String,Any> =
        mapOf(
            "lastMessage" to lastMessage,
            "lastMessageBy" to lastMessageBy,
            "lastMessageCount" to FieldValue.increment(1),
            "lastMessageRead" to false
        )

}