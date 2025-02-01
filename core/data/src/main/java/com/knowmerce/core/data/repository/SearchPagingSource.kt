package com.knowmerce.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.model.SearchImage
import com.knowmerce.core.domain.model.SearchVideoClip

class SearchPagingSource(
    private val kakaoDataSource: KakaoDataSource,
    private val query: String,
) : PagingSource<Int, SearchContent>() {
    override fun getRefreshKey(state: PagingState<Int, SearchContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchContent> {
        return try {
            val page = params.key ?: 1
            val size = params.loadSize

            val images = kakaoDataSource.searchImage(
                query = query,
                page = page,
                size = size
            )
            val videos = kakaoDataSource.searchVideoClip(
                query = query,
                page = page,
                size = size
            )

            val searchContents = mutableListOf<SearchContent>()
            images.documents.map {
                searchContents.add(
                    SearchImage(
                        thumbnailUrl = it.thumbnailUrl,
                        title = it.displaySiteName,
                        datetime = it.datetime,
                    )
                )
            }
            videos.documents.map {
                searchContents.add(
                    SearchVideoClip(
                        thumbnailUrl = it.thumbnail,
                        title = it.title,
                        datetime = it.datetime,
                        playTime = it.playTime,
                    )
                )
            }
            searchContents.sortByDescending { it.datetime }

            LoadResult.Page(
                data = searchContents,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (searchContents.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}