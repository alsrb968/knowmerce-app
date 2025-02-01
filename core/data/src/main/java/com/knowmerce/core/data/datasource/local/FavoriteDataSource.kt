package com.knowmerce.core.data.datasource.local

import androidx.paging.PagingSource
import com.knowmerce.core.data.db.FavoriteDao
import com.knowmerce.core.data.model.local.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoriteDataSource {
    suspend fun insertFavorite(favorite: FavoriteEntity)
    fun isFavorite(thumbnailUrl: String): Flow<Boolean>
    fun getFavorites(): PagingSource<Int, FavoriteEntity>
    suspend fun deleteFavorite(thumbnailUrl: String)
    suspend fun deleteAllFavorites()
}

class FavoriteDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoriteDataSource {
    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        favoriteDao.insertFavorite(favorite)
    }

    override fun isFavorite(thumbnailUrl: String): Flow<Boolean> {
        return favoriteDao.isFavorite(thumbnailUrl)
    }

    override fun getFavorites(): PagingSource<Int, FavoriteEntity> {
        return favoriteDao.getFavorites()
    }

    override suspend fun deleteFavorite(thumbnailUrl: String) {
        favoriteDao.deleteFavorite(thumbnailUrl)
    }

    override suspend fun deleteAllFavorites() {
        favoriteDao.deleteAllFavorites()
    }
}