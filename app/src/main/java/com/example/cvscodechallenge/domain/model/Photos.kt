package com.example.cvscodechallenge.domain.model

data class Photos(
    val title: String?,
    val link: String?,
    val description: String?,
    val modified: String?,
    val generator: String?,
    val items: List<Photo>
)