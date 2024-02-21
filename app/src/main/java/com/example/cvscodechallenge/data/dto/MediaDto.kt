package com.example.cvscodechallenge.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaDto(
    @field:Json(name = "m") val m: String?
)