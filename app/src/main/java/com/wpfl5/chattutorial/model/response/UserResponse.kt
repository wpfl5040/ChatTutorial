package com.wpfl5.chattutorial.model.response

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.storage.StorageReference
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class UserResponse(
    val uid: String,
    val id: String,
    val name: String,
    val fcmToken: String?,
    val profileImage: String?,
) : Parcelable {
    constructor() : this("", "", "", "", "")

    @IgnoredOnParcel
    var profile : StorageReference? = null
}