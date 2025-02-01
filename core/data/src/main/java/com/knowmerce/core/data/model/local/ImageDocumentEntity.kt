package com.knowmerce.core.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_document")
data class ImageDocumentEntity(
    @PrimaryKey val keyword: String,
    val timestamp: Long,
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: String,
)
