package com.example.cvscodechallenge.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvscodechallenge.domain.model.FlickrSearchUiState
import com.example.cvscodechallenge.domain.model.NetworkResult
import com.example.cvscodechallenge.domain.model.Photo
import com.example.cvscodechallenge.domain.repository.FlickrSearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlickrSearchViewModel(private val flickrSearchRepository: FlickrSearchRepository) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var selectedImage by mutableStateOf<Photo?>(null)
        private set

    private val _flickrSearchImages =
        MutableStateFlow<FlickrSearchUiState>(FlickrSearchUiState.Empty)
    val flickrSearchImages = _flickrSearchImages.asStateFlow()

    init {
        viewModelScope.launch {
            flickrSearchRepository.searchResult.collect {
                _flickrSearchImages.value = when (it) {
                    is NetworkResult.Success -> FlickrSearchUiState.Success(it.data)
                    is NetworkResult.Error -> FlickrSearchUiState.Error(it.errorMessage)
                    else -> FlickrSearchUiState.Empty
                }
            }
        }
    }

    fun onSearchQueryChange(word: String) {
        searchQuery = word
        viewModelScope.launch {
            _flickrSearchImages.value = FlickrSearchUiState.Loading
            flickrSearchRepository.searchFlickr(word)
        }
    }

    fun onClickImage(photo: Photo) {
        selectedImage = photo
    }
}