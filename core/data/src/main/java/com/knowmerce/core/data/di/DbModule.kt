package com.knowmerce.core.data.di

import android.content.Context
import com.knowmerce.core.data.db.ImageDocumentDao
import com.knowmerce.core.data.db.SearchDatabase
import com.knowmerce.core.data.db.VideoClipDocumentDao
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
    fun provideImageDocumentDao(
        database: SearchDatabase
    ): ImageDocumentDao = database.imageDocumentDao()

    @Provides
    @Singleton
    fun provideVideoClipDocumentDao(
        database: SearchDatabase
    ): VideoClipDocumentDao = database.videoClipDocumentDao()
}