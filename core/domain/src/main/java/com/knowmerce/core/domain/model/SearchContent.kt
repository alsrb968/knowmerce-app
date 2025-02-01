package com.knowmerce.core.domain.model

import com.knowmerce.core.domain.extensions.ConvertExtensions.toDateTime
import com.knowmerce.core.domain.extensions.ConvertExtensions.toPlayTime

data class SearchContent(
    val thumbnailUrl: String,
    val title: String,
    val datetime: String,
    val playTime: Int? = null,
) {
    val displayedDatetime: String
        get() = datetime.toDateTime()

    val displayedPlayTime: String?
        get() = playTime?.toPlayTime()
}