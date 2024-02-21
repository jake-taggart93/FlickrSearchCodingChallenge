package com.example.cvscodechallenge.data.converter

import com.example.cvscodechallenge.data.dto.MediaDto
import com.example.cvscodechallenge.data.dto.PhotoDto
import com.example.cvscodechallenge.data.dto.PhotosDto
import com.example.cvscodechallenge.domain.model.Media
import com.example.cvscodechallenge.domain.model.Photo
import com.example.cvscodechallenge.domain.model.Photos

fun PhotosDto.toPhotos() = Photos(
    title = title,
    link = link,
    description = description,
    modified = modified,
    generator = generator,
    items = items.map { it.toPhoto() }
)

fun PhotoDto.toPhoto() = Photo(
    title = title,
    link = link,
    media = media?.toMedia(),
    dateTaken = dateTaken,
    description = description,
    published = published,
    author = author,
    authorId = authorId,
    tags = tags
)

fun MediaDto.toMedia() = Media(
    m = m
)