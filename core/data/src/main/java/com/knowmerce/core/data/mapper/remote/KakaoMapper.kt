package com.knowmerce.core.data.mapper.remote

import com.knowmerce.core.data.model.remote.*
import com.knowmerce.core.domain.model.*

fun MetaResponse.toMeta(): Meta {
    return Meta(
        totalCount = totalCount,
        pageableCount = pageableCount,
        isEnd = isEnd,
    )
}

fun ImageDocumentResponse.toImageDocument(): ImageDocument {
    return ImageDocument(
        collection = collection,
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        width = width,
        height = height,
        displaySiteName = displaySiteName,
        docUrl = docUrl,
        datetime = datetime,
    )
}

fun VideoClipDocumentResponse.toVideoClipDocument(): VideoClipDocument {
    return VideoClipDocument(
        title = title,
        url = url,
        datetime = datetime,
        playTime = playTime,
        thumbnail = thumbnail,
        author = author,
    )
}

fun ImageSearchResponse.toImageSearch(): ImageSearch {
    return ImageSearch(
        meta = meta.toMeta(),
        documents = documents.map { it.toImageDocument() },
    )
}

fun VideoClipSearchResponse.toVideoClipSearch(): VideoClipSearch {
    return VideoClipSearch(
        meta = meta.toMeta(),
        documents = documents.map { it.toVideoClipDocument() },
    )
}