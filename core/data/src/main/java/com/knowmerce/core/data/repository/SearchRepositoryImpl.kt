package com.knowmerce.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.knowmerce.core.data.datasource.local.DocumentDataSource
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.mapper.local.toDocument
import com.knowmerce.core.data.mediator.SearchRemoteMediator
import com.knowmerce.core.domain.mapper.toSearchContent
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val documentDataSource: DocumentDataSource,
    private val kakaoDataSource: KakaoDataSource,
) : SearchRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun search(query: String, pageSize: Int): Flow<PagingData<SearchContent>> {
        return flow {
            documentDataSource.deleteExpiredDocument(
                expiredTimestamp = SearchRepository.before5MinutesTimestamp,
            )

            emitAll(
                Pager(
                    config = PagingConfig(
                        pageSize = pageSize,
                        prefetchDistance = 5,
                        enablePlaceholders = false,
                    ),
                    remoteMediator = SearchRemoteMediator(
                        local = documentDataSource,
                        remote = kakaoDataSource,
                        query = query,
                    ),
                    pagingSourceFactory = {
                        documentDataSource.getValidDocument(
                            keyword = query,
                            validTimestamp = SearchRepository.before5MinutesTimestamp,
                        )
                    }
                ).flow.map { pagingData ->
                    pagingData.map { it.toDocument().toSearchContent() }
                }
            )
        }
    }
}