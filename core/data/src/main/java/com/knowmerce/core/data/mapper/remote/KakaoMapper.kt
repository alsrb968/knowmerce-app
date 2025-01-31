package com.knowmerce.core.data.mapper.remote

import com.knowmerce.core.data.model.remote.ImageDocumentResponse
import com.knowmerce.core.data.model.remote.ImageSearchResponse
import com.knowmerce.core.data.model.remote.MetaResponse
import com.knowmerce.core.data.model.remote.VideoClipDocumentResponse
import com.knowmerce.core.data.model.remote.VideoClipSearchResponse
import com.knowmerce.core.domain.model.remote.ImageDocument
import com.knowmerce.core.domain.model.remote.ImageSearch
import com.knowmerce.core.domain.model.remote.Meta
import com.knowmerce.core.domain.model.remote.VideoClipDocument
import com.knowmerce.core.domain.model.remote.VideoClipSearch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun String.toDateTime(): String {
    val dt = OffsetDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("yyyy.M.d")
    return dt.format(formatter)
}

fun Int.toPlayTime(): String {
    val minutes = this / 60
    val seconds = this % 60
    return "%d:%02d".format(minutes, seconds)
}

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
        datetime = datetime.toDateTime(),
    )
}

fun VideoClipDocumentResponse.toVideoClipDocument(): VideoClipDocument {
    return VideoClipDocument(
        title = title,
        url = url,
        datetime = datetime.toDateTime(),
        playTime = playTime.toPlayTime(),
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