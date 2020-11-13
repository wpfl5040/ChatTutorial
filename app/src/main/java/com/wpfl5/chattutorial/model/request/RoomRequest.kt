package com.wpfl5.chattutorial.model.request

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomRequest(
    val createdBy: String,
    val users: List<String>,
    @ServerTimestamp val createdAt: Timestamp?
) : Parcelable {

    constructor(createdBy: String, users: List<String>): this(createdBy, users, Timestamp.now())

//    fun getMapData() : Map<String,Any> =
//        mapOf(
//            "lastMessage" to lastMessage,
//            "lastMessageBy" to lastMessageBy,
//            "lastMessageCount" to FieldValue.increment(1),
//            "lastMessageRead" to false
//        )

}