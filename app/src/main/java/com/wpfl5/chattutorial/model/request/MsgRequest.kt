package com.wpfl5.chattutorial.model.request

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MsgRequest(
    val msg: String,
    val receiveBy: String,
    val sentBy: String,
    @ServerTimestamp val sentAt: Timestamp?
) : Parcelable {
    constructor(msg: String, receiveBy: String, sentBy: String)
            : this(msg,receiveBy,sentBy,Timestamp.now())
}