package com.knowmerce.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knowmerce.core.data.model.local.VideoClipDocumentEntity

@Dao
interface VideoClipDocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoClipDocument(videoClipDocument: List<VideoClipDocumentEntity>)

    @Query(
        """
        SELECT *
        FROM video_clip_document
        WHERE keyword = :keyword AND timestamp > :validTimestamp
        """
    )
    suspend fun getValidVideoClipDocument(keyword: String, validTimestamp: Long): List<VideoClipDocumentEntity>

    @Query(
        """
        DELETE
        FROM video_clip_document
        WHERE timestamp < :expiredTimestamp
        """
    )
    suspend fun deleteExpiredVideoClipDocument(expiredTimestamp: Long)
}