package com.knowmerce.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.knowmerce.core.data.datasource.local.DocumentDataSource
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.mapper.local.toDocumentEntity
import com.knowmerce.core.data.model.local.DocumentEntity
import com.knowmerce.core.domain.repository.SearchRepository

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val local: DocumentDataSource,
    private val remote: KakaoDataSource,
    private val query: String,
) : RemoteMediator<Int, DocumentEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DocumentEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val lastItem = local.getLastDocument(query)
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.page + 1
            }
        }

        return try {
            val size = state.config.pageSize / 2

            val images = remote.searchImage(
                query = query,
                page = page,
                size = size
            )
            val videos = remote.searchVideoClip(
                query = query,
                page = page,
                size = size
            )

//            if (loadType == LoadType.REFRESH) {
//                local.deleteAllDocument()
//            }
            val timestamp = SearchRepository.currentTimestamp
            val documents =
                images.documents.map {
                    it.toDocumentEntity(
                        keyword = query,
                        timestamp = timestamp,
                        page = page,
                    )
                } + videos.documents.map {
                    it.toDocumentEntity(
                        keyword = query,
                        timestamp = timestamp,
                        page = page,
                    )
                }

            local.insertDocument(documents.sortedByDescending { it.datetime })

            MediatorResult.Success(endOfPaginationReached = images.meta.isEnd && videos.meta.isEnd)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}