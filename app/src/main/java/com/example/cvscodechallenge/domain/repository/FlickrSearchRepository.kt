package com.example.cvscodechallenge.domain.repository

import com.example.cvscodechallenge.domain.model.NetworkResult
import com.example.cvscodechallenge.domain.model.Photos
import kotlinx.coroutines.flow.Flow

interface FlickrSearchRepository {
    val searchResult: Flow<NetworkResult<Photos>>
    suspend fun searchFlickr(searchTerm: String)
}