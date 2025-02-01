package com.knowmerce.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.knowmerce.core.data.datasource.local.DocumentDataSource
import com.knowmerce.core.data.datasource.local.FavoriteDataSource
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.mapper.local.toDocument
import com.knowmerce.core.data.mapper.local.toFavoriteEntity
import com.knowmerce.core.data.mapper.local.toSearchContent
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
    private val favoriteDataSource: FavoriteDataSource,
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

    override suspend fun addFavorite(searchContent: SearchContent) {
        favoriteDataSource.insertFavorite(searchContent.toFavoriteEntity())
    }

    override suspend fun removeFavorite(searchContent: SearchContent) {
        favoriteDataSource.deleteFavorite(searchContent.thumbnailUrl)
    }

    override suspend fun removeAllFavorites() {
        favoriteDataSource.deleteAllFavorites()
    }

    override fun isFavorite(searchContent: SearchContent): Flow<Boolean> {
        return favoriteDataSource.isFavorite(searchContent.thumbnailUrl)
    }

    override fun getFavorites(pageSize: Int): Flow<PagingData<SearchContent>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { favoriteDataSource.getFavorites() }
        ).flow.map { pagingData ->
            pagingData.map { it.toSearchContent() }
        }
    }


}