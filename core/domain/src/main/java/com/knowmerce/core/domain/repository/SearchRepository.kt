package com.knowmerce.core.domain.repository

import androidx.paging.PagingData
import com.knowmerce.core.domain.model.SearchContent
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    companion object {
        private const val CACHE_DURATION = 5 * 60 // 5 minutes
        val currentTimestamp: Long
            get() = System.currentTimeMillis() / 1000
        val before5MinutesTimestamp = currentTimestamp - CACHE_DURATION
    }

    fun search(query: String, pageSize: Int = 20): Flow<PagingData<SearchContent>>
    suspend fun addFavorite(searchContent: SearchContent)
    suspend fun removeFavorite(searchContent: SearchContent)
    suspend fun removeAllFavorites()
    fun isFavorite(searchContent: SearchContent): Flow<Boolean>
    fun getFavorites(pageSize: Int = 20): Flow<PagingData<SearchContent>>
}