package com.example.cvscodechallenge.domain.model

sealed class NetworkResult<out T : Any> {
    data object Empty: NetworkResult<Nothing>()
    data class Success<out T: Any>(val data: T) : NetworkResult<T>()
    data class Error(val errorMessage: String) : NetworkResult<Nothing>()
}