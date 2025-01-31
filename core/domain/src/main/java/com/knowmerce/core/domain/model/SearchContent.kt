package com.knowmerce.core.domain.model

import com.knowmerce.core.domain.extensions.ConvertExtensions.toDateTime

interface SearchContent {
    val thumbnailUrl: String
    val title: String
    val datetime: String
}

val SearchContent.displayedDatetime: String
    get() = datetime.toDateTime()
