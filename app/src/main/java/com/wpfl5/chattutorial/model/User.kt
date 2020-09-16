package com.wpfl5.chattutorial.model

data class User (
    val id: String,
    val password: String,
    val name: String?
) {
    constructor(id: String, pwd: String) : this(id, pwd, null)
}