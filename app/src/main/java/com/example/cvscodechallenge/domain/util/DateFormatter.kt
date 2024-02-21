package com.example.cvscodechallenge.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String?.formatDate(): String {
    //"yyyy-MM-dd'T'HH:mm:ss"
    val date =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(this.orEmpty())
    return SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date ?: Date())
}