package com.wpfl5.chattutorial.model.response

sealed class FbResponse<out T> {
    object Loading : FbResponse<Nothing>()
    data class Success<out T>(val data: T) : FbResponse<T>()
    data class Fail(val e: Exception) : FbResponse<Nothing>()
}