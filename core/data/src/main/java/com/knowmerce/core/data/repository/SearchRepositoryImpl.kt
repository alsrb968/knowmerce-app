package com.knowmerce.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.knowmerce.core.data.datasource.local.DocumentDataSource
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.mapper.remote.toImageSearch
import com.knowmerce.core.data.mapper.remote.toVideoClipSearch
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.model.remote.ImageSearch
import com.knowmerce.core.domain.model.remote.VideoClipSearch
import com.knowmerce.core.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val documentDataSource: DocumentDataSource,
    private val kakaoDataSource: KakaoDataSource,
) : SearchRepository {
    override suspend fun searchImage(query: String, sort: String, page: Int, size: Int): ImageSearch {
        return kakaoDataSource.searchImage(query, sort, page, size).toImageSearch()
    }

    override suspend fun searchVideoClip(query: String, sort: String, page: Int, size: Int): VideoClipSearch {
        return kakaoDataSource.searchVideoClip(query, sort, page, size).toVideoClipSearch()
    }

    override fun search(query: String, pageSize: Int): Flow<PagingData<SearchContent>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { SearchPagingSource(kakaoDataSource, query) }
        ).flow
    }
}