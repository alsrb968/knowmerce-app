package com.knowmerce.core.domain.model

data class SearchVideoClip(
    override val thumbnailUrl: String,
    override val title: String,
    override val datetime: String,
    val playTime: String,
) : SearchContent
