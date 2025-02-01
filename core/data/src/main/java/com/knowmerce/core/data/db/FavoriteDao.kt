package com.knowmerce.core.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knowmerce.core.data.model.local.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query(
        """
        SELECT 1
        FROM favorite
        WHERE thumbnailUrl = :thumbnailUrl
        """
    )
    fun isFavorite(thumbnailUrl: String): Flow<Boolean>

    @Query(
        """
        SELECT *
        FROM favorite
        ORDER BY id DESC
        """
    )
    fun getFavorites(): PagingSource<Int, FavoriteEntity>

    @Query(
        """
        DELETE
        FROM favorite
        WHERE thumbnailUrl = :thumbnailUrl
        """
    )
    suspend fun deleteFavorite(thumbnailUrl: String)

    @Query(
        """
        DELETE
        FROM favorite
        """
    )
    suspend fun deleteAllFavorites()
}