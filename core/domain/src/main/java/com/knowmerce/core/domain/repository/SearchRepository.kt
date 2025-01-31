package com.knowmerce.core.domain.repository

import com.knowmerce.core.domain.model.remote.ImageSearch
import com.knowmerce.core.domain.model.remote.VideoClipSearch

interface SearchRepository {
    suspend fun searchImage(
        query: String,
        sort: String = "accuracy",
        page: Int,
        size: Int,
    ): ImageSearch

    suspend fun searchVideoClip(
        query: String,
        sort: String = "accuracy",
        page: Int,
        size: Int,
    ): VideoClipSearch
}