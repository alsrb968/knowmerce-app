package com.knowmerce.core.domain.model

import com.knowmerce.core.domain.extensions.ConvertExtensions.toPlayTime

data class SearchVideoClip(
    override val thumbnailUrl: String,
    override val title: String,
    override val datetime: String,
    val playTime: Int,
) : SearchContent {
    val displayedPlayTime: String
        get() = playTime.toPlayTime()
}
