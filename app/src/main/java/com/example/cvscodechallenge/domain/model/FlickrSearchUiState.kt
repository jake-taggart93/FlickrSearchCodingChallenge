package com.example.cvscodechallenge.domain.model

sealed class FlickrSearchUiState {
    data object Empty: FlickrSearchUiState()
    data object Loading : FlickrSearchUiState()
    data class Error(val errorMessage: String): FlickrSearchUiState()
    data class Success(val data: Photos): FlickrSearchUiState()
}