package com.knowmerce.core.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_clip_document")
data class VideoClipDocumentEntity(
    @PrimaryKey val keyword: String,
    val timestamp: Long,
    val title: String,
    val url: String,
    val datetime: String,
    val playTime: Int,
    val thumbnailUrl: String,
    val author: String,
)
