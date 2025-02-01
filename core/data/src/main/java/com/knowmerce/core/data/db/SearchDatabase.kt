package com.knowmerce.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.knowmerce.core.data.model.local.DocumentEntity
import com.knowmerce.core.data.model.local.FavoriteEntity

@Database(
    entities = [
        DocumentEntity::class,
        FavoriteEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private const val DB_NAME = "search.db"

        fun getInstance(context: Context): SearchDatabase =
            Room.databaseBuilder(context, SearchDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}