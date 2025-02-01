package com.knowmerce.core.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val thumbnailUrl: String,
    val title: String,
    val datetime: String,
    val playTime: Int? = null,
)
