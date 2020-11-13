package com.wpfl5.chattutorial.model.response

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomResponse(
    @DocumentId
    val rid: String,
    val lastMessage: String,
    val createdBy: String,
    val users: List<String>,
    @ServerTimestamp val createdAt: Timestamp?,
    @ServerTimestamp val modifiedAt: Timestamp?
) : Parcelable {
    constructor() : this("","","", listOf<String>(),null, null)
}