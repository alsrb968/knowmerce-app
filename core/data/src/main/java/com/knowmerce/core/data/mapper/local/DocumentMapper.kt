package com.knowmerce.core.data.mapper.local

import com.knowmerce.core.data.model.local.DocumentEntity
import com.knowmerce.core.data.model.remote.ImageDocumentResponse
import com.knowmerce.core.data.model.remote.VideoClipDocumentResponse
import com.knowmerce.core.domain.model.local.Document

fun ImageDocumentResponse.toDocumentEntity(
    keyword: String,
    timestamp: Long,
    page: Int,
): DocumentEntity {
    return DocumentEntity(
        keyword = keyword,
        timestamp = timestamp,
        page = page,
        thumbnailUrl = thumbnailUrl,
        title = displaySiteName,
        datetime = datetime,
    )
}

fun VideoClipDocumentResponse.toDocumentEntity(
    keyword: String,
    timestamp: Long,
    page: Int,
): DocumentEntity {
    return DocumentEntity(
        keyword = keyword,
        timestamp = timestamp,
        page = page,
        thumbnailUrl = thumbnail,
        title = title,
        datetime = datetime,
        playTime = playTime,
    )
}

fun DocumentEntity.toDocument(): Document {
    return Document(
        keyword = keyword,
        timestamp = timestamp,
        page = page,
        thumbnailUrl = thumbnailUrl,
        title = title,
        datetime = datetime,
        playTime = playTime,
    )
}