package com.knowmerce.core.data.datasource.local

import androidx.paging.PagingSource
import com.knowmerce.core.data.db.DocumentDao
import com.knowmerce.core.data.model.local.DocumentEntity
import javax.inject.Inject

interface DocumentDataSource {
    suspend fun insertDocument(document: List<DocumentEntity>)

    fun getValidDocument(
        keyword: String,
        validTimestamp: Long
    ): PagingSource<Int, DocumentEntity>

    suspend fun deleteExpiredDocument(expiredTimestamp: Long)

    suspend fun deleteAllDocument()
}

class DocumentDataSourceImpl @Inject constructor(
    private val documentDao: DocumentDao,
) : DocumentDataSource {
    override suspend fun insertDocument(document: List<DocumentEntity>) {
        documentDao.insertDocument(document)
    }

    override fun getValidDocument(
        keyword: String,
        validTimestamp: Long
    ): PagingSource<Int, DocumentEntity> {
        return documentDao.getValidDocument(keyword, validTimestamp)
    }

    override suspend fun deleteExpiredDocument(expiredTimestamp: Long) {
        documentDao.deleteExpiredDocument(expiredTimestamp)
    }

    override suspend fun deleteAllDocument() {
        documentDao.deleteAllDocument()
    }
}