package com.knowmerce.core.data.datasource.local

import com.knowmerce.core.data.db.ImageDocumentDao
import com.knowmerce.core.data.db.VideoClipDocumentDao
import com.knowmerce.core.data.model.local.ImageDocumentEntity
import com.knowmerce.core.data.model.local.VideoClipDocumentEntity
import javax.inject.Inject

interface DocumentDataSource {
    suspend fun insertImageDocument(imageDocument: List<ImageDocumentEntity>)
    suspend fun getValidImageDocument(
        keyword: String,
        validTimestamp: Long
    ): List<ImageDocumentEntity>

    suspend fun deleteExpiredImageDocument(expiredTimestamp: Long)

    suspend fun insertVideoClipDocument(videoDocument: List<VideoClipDocumentEntity>)
    suspend fun getValidVideoClipDocument(
        keyword: String,
        validTimestamp: Long
    ): List<VideoClipDocumentEntity>

    suspend fun deleteExpiredVideoClipDocument(expiredTimestamp: Long)
}

class DocumentDataSourceImpl @Inject constructor(
    private val imageDocumentDao: ImageDocumentDao,
    private val videoClipDocumentDao: VideoClipDocumentDao,
) : DocumentDataSource {
    override suspend fun insertImageDocument(imageDocument: List<ImageDocumentEntity>) {
        imageDocumentDao.insertImageDocument(imageDocument)
    }

    override suspend fun getValidImageDocument(
        keyword: String,
        validTimestamp: Long
    ): List<ImageDocumentEntity> {
        return imageDocumentDao.getValidImageDocument(keyword, validTimestamp)
    }

    override suspend fun deleteExpiredImageDocument(expiredTimestamp: Long) {
        imageDocumentDao.deleteExpiredImageDocument(expiredTimestamp)
    }

    override suspend fun insertVideoClipDocument(videoDocument: List<VideoClipDocumentEntity>) {
        videoClipDocumentDao.insertVideoClipDocument(videoDocument)
    }

    override suspend fun getValidVideoClipDocument(
        keyword: String,
        validTimestamp: Long
    ): List<VideoClipDocumentEntity> {
        return videoClipDocumentDao.getValidVideoClipDocument(keyword, validTimestamp)
    }

    override suspend fun deleteExpiredVideoClipDocument(expiredTimestamp: Long) {
        videoClipDocumentDao.deleteExpiredVideoClipDocument(expiredTimestamp)
    }
}