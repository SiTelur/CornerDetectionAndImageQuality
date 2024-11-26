package com.nbs.cornerdetectiondimagequality.data

sealed class Resource<out T: Any?> {
    object Idle : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String?) : Resource<T>()
}