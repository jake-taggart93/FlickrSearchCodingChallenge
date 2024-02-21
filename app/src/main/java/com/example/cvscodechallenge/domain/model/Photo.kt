package com.example.cvscodechallenge.domain.model

data class Photo(
    val title: String?,
    val link: String?,
    val media: Media?,
    val dateTaken: String?,
    val description: String?,
    val published: String?,
    val author: String?,
    val authorId: String?,
    val tags: String?
)
