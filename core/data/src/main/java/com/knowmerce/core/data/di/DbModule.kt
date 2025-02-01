package com.knowmerce.core.data.di

import android.content.Context
import com.knowmerce.core.data.db.DocumentDao
import com.knowmerce.core.data.db.FavoriteDao
import com.knowmerce.core.data.db.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): SearchDatabase {
        return SearchDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDocumentDao(
        database: SearchDatabase
    ): DocumentDao = database.documentDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(
        database: SearchDatabase
    ): FavoriteDao = database.favoriteDao()
}