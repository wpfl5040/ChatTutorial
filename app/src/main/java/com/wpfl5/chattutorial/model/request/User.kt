package com.wpfl5.chattutorial.model.request

data class AuthUser (
    val id: String,
    val password: String
)

data class User(
    val uid: String,
    val id: String,
    val name: String,
    val fcmToken: String?
)