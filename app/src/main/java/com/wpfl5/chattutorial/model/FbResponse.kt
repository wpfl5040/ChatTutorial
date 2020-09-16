package com.wpfl5.chattutorial.model

sealed class FbResponse<out T> {
    object Loading : FbResponse<Nothing>()
    data class Success<out T>(val data: T) : FbResponse<T>()
    data class Fail<out T>(val e: Exception) : FbResponse<T>()
}