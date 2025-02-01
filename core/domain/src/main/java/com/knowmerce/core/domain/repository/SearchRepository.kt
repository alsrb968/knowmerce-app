package com.knowmerce.core.domain.repository

import androidx.paging.PagingData
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.model.remote.ImageSearch
import com.knowmerce.core.domain.model.remote.VideoClipSearch
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    companion object {
        const val CACHE_DURATION = 5 * 60 * 1000 // 5 minutes
    }

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

    fun search(
        query: String,
        pageSize: Int = 20,
    ): Flow<PagingData<SearchContent>>
}