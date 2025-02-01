package com.knowmerce.core.data.di

import com.knowmerce.core.data.api.KakaoApi
import com.knowmerce.core.data.datasource.local.DocumentDataSource
import com.knowmerce.core.data.datasource.local.DocumentDataSourceImpl
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.datasource.remote.KakaoDataSourceImpl
import com.knowmerce.core.data.db.DocumentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideDocumentDataSource(
        documentDao: DocumentDao
    ): DocumentDataSource {
        return DocumentDataSourceImpl(
            documentDao = documentDao
        )
    }

    @Provides
    @Singleton
    fun provideKakaoDataSource(
        api: KakaoApi
    ): KakaoDataSource {
        return KakaoDataSourceImpl(
            api = api
        )
    }
}