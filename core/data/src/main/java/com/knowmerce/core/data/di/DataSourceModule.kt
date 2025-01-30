package com.knowmerce.core.data.di

import com.knowmerce.core.data.api.KakaoApi
import com.knowmerce.core.data.datasource.remote.KakaoDataSource
import com.knowmerce.core.data.datasource.remote.KakaoDataSourceImpl
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
    fun provideKakaoDataSource(
        api: KakaoApi
    ): KakaoDataSource {
        return KakaoDataSourceImpl(
            api = api
        )
    }
}