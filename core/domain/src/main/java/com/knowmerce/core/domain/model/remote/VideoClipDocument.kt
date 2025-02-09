package com.knowmerce.core.domain.model.remote

data class VideoClipDocument(
    val title: String,
    val url: String,
    val datetime: String,
    val playTime: Int,
    val thumbnail: String,
    val author: String,
)
