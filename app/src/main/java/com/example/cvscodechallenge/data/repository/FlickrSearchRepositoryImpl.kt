package com.example.cvscodechallenge.data.repository

import com.example.cvscodechallenge.data.converter.toPhotos
import com.example.cvscodechallenge.data.network.FlickrNetworkSource
import com.example.cvscodechallenge.domain.model.NetworkResult
import com.example.cvscodechallenge.domain.model.Photos
import com.example.cvscodechallenge.domain.repository.FlickrSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FlickrSearchRepositoryImpl(private val networkSource: FlickrNetworkSource) : FlickrSearchRepository {
    private val _searchResult: MutableStateFlow<NetworkResult<Photos>> =
        MutableStateFlow(NetworkResult.Empty)
    override val searchResult: Flow<NetworkResult<Photos>>
        get() = _searchResult

    override suspend fun searchFlickr(searchTerm: String) {
        try {
            networkSource.flickrSearchService.getPhotos(searchTerm = searchTerm).also {
                if (it.isSuccessful) {
                    _searchResult.value =
                        NetworkResult.Success(data = it.body()?.toPhotos() as Photos)
                } else {
                    _searchResult.value = NetworkResult.Error(
                        errorMessage = it.message(),
                    )
                }
            }
        } catch (e: Exception) {
            _searchResult.value = NetworkResult.Error(
                errorMessage = e.message ?: "Unexpected Error"
            )
        }
    }
}