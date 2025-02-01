package com.knowmerce.core.domain.model.local

data class Document(
    val keyword: String,
    val timestamp: Long,
    val page: Int,
    val thumbnailUrl: String,
    val title: String,
    val datetime: String,
    val playTime: Int? = null,
)
