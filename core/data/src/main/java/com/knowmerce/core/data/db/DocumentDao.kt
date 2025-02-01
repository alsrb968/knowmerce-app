package com.knowmerce.core.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knowmerce.core.data.model.local.DocumentEntity

@Dao
interface DocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: List<DocumentEntity>)

    @Query(
        """
        SELECT *
        FROM document
        WHERE keyword = :keyword AND timestamp > :validTimestamp
        ORDER BY datetime DESC
        """
    )
    fun getValidDocument(
        keyword: String,
        validTimestamp: Long
    ): PagingSource<Int, DocumentEntity>

    @Query(
        """
        DELETE
        FROM document
        WHERE timestamp <= :expiredTimestamp
        """
    )
    suspend fun deleteExpiredDocument(expiredTimestamp: Long)
}