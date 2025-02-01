package com.knowmerce.core.data.di

import com.knowmerce.core.data.datasource.local.DocumentDataSource
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.repository.SearchRepositoryImpl
import com.knowmerce.core.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSearchRepository(
        documentDataSource: DocumentDataSource,
        kakaoDataSource: KakaoDataSource,
    ): SearchRepository {
        return SearchRepositoryImpl(
            documentDataSource = documentDataSource,
            kakaoDataSource = kakaoDataSource,
        )
    }
}