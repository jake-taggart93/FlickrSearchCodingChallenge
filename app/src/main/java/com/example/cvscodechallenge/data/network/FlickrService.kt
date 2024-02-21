package com.example.cvscodechallenge.data.network

import com.example.cvscodechallenge.data.dto.PhotosDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("services/feeds/photos_public.gne") //https://api.flickr.com/services/feeds/photos_public.gne?format=json&tags=dog
    suspend fun getPhotos(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallBack: String = "1",
        @Query("tags") searchTerm: String
    ): Response<PhotosDto>
}