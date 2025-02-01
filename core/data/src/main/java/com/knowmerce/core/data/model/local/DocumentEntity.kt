package com.knowmerce.core.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "document")
data class DocumentEntity(
    val keyword: String,
    val timestamp: Long,
    val page: Int,
    @PrimaryKey val thumbnailUrl: String,
    val title: String,
    val datetime: String,
    val playTime: Int? = null,
)
