package com.wpfl5.chattutorial.model.response

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MsgResponse(
    @DocumentId
    val mId: String,
    val msg: String,
    val receiveBy: String,
    val sentBy: String,
    @ServerTimestamp val sentAt: Timestamp?
) : Parcelable {
    constructor() : this("","","","",null)
    constructor(msg: String, receiveBy: String, sentBy: String, sentAt: Timestamp?) : this("",msg,receiveBy,sentBy,sentAt)
}