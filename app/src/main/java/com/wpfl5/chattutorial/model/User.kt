package com.wpfl5.chattutorial.model

data class AuthUser (
    val id: String,
    val password: String
)

data class User(
    val id: String,
    val password: String,
    val name: String,
    val fcmToken: String?
)