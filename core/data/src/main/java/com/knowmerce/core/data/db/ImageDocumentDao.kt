package com.knowmerce.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knowmerce.core.data.model.local.ImageDocumentEntity

@Dao
interface ImageDocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageDocument(imageDocument: List<ImageDocumentEntity>)

    @Query(
        """
        SELECT *
        FROM image_document
        WHERE keyword = :keyword AND timestamp > :validTimestamp
        """
    )
    suspend fun getValidImageDocument(keyword: String, validTimestamp: Long): List<ImageDocumentEntity>

    @Query(
        """
        DELETE
        FROM image_document
        WHERE timestamp < :expiredTimestamp
        """
    )
    suspend fun deleteExpiredImageDocument(expiredTimestamp: Long)
}