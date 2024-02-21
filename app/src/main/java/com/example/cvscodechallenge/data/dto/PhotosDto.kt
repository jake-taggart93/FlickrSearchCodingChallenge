package com.example.cvscodechallenge.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosDto(
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "link") val link: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "modified") val modified: String?,
    @field:Json(name = "generator") val generator: String?,
    @field:Json(name = "items") val items: List<PhotoDto>
)
