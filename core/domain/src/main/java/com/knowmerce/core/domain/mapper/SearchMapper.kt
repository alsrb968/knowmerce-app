package com.knowmerce.core.domain.mapper

import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.model.local.Document

fun Document.toSearchContent(): SearchContent {
    return SearchContent(
        thumbnailUrl = thumbnailUrl,
        title = title,
        datetime = datetime,
        playTime = playTime,
    )
}