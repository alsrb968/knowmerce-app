package com.knowmerce.core.data.mapper.local

import com.knowmerce.core.data.model.local.FavoriteEntity
import com.knowmerce.core.domain.model.SearchContent

fun FavoriteEntity.toSearchContent(): SearchContent {
    return SearchContent(
        thumbnailUrl = thumbnailUrl,
        title = title,
        datetime = datetime,
        playTime = playTime,
    )
}

fun SearchContent.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        thumbnailUrl = thumbnailUrl,
        title = title,
        datetime = datetime,
        playTime = playTime,
    )
}