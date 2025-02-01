package com.knowmerce.core.data.datasource.remote

import com.knowmerce.core.data.BuildConfig
import com.knowmerce.core.data.api.KakaoApi
import com.knowmerce.core.data.model.remote.ImageSearchResponse
import com.knowmerce.core.data.model.remote.VideoClipSearchResponse
import javax.inject.Inject

interface KakaoDataSource {
    suspend fun searchImage(
        query: String,
        sort: String = "recency",
        page: Int,
        size: Int,
    ): ImageSearchResponse

    suspend fun searchVideoClip(
        query: String,
        sort: String = "recency",
        page: Int,
        size: Int,
    ): VideoClipSearchResponse
}

class KakaoDataSourceImpl @Inject constructor(
    private val api: KakaoApi,
) : KakaoDataSource {
    private val apiKey = "KakaoAK ${BuildConfig.KAKAO_KEY}"

    override suspend fun searchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): ImageSearchResponse {
        return api.searchImage(
            query = query,
            sort = sort,
            page = page,
            size = size,
            apiKey = apiKey,
        )
    }

    override suspend fun searchVideoClip(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): VideoClipSearchResponse {
        return api.searchVideoClip(
            query = query,
            sort = sort,
            page = page,
            size = size,
            apiKey = apiKey,
        )
    }
}