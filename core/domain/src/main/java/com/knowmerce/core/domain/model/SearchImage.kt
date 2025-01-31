package com.knowmerce.core.domain.model

data class SearchImage(
    override val thumbnailUrl: String,
    override val title: String,
    override val datetime: String,
) : SearchContent
