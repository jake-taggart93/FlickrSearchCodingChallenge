package com.example.cvscodechallenge.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDto(
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "link") val link: String?,
    @field:Json(name = "media") val media: MediaDto?,
    @field:Json(name = "date_taken") val dateTaken: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "published") val published: String?,
    @field:Json(name = "author") val author: String?,
    @field:Json(name = "author_id") val authorId: String?,
    @field:Json(name = "tags") val tags: String?
)