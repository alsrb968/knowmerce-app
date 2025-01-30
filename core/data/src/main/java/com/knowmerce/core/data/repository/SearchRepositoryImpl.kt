package com.knowmerce.core.data.repository

import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.mapper.remote.toImageSearch
import com.knowmerce.core.data.mapper.remote.toVideoClipSearch
import com.knowmerce.core.domain.model.ImageSearch
import com.knowmerce.core.domain.model.VideoClipSearch
import com.knowmerce.core.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: KakaoDataSource
) : SearchRepository {
    override suspend fun searchImage(query: String, sort: String, page: Int, size: Int): ImageSearch {
        return remoteDataSource.searchImage(query, sort, page, size).toImageSearch()
    }

    override suspend fun searchVideoClip(query: String, sort: String, page: Int, size: Int): VideoClipSearch {
        return remoteDataSource.searchVideoClip(query, sort, page, size).toVideoClipSearch()
    }
}