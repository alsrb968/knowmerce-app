package com.knowmerce.core.data.di

import com.knowmerce.core.data.api.KakaoApi
import com.knowmerce.core.data.api.KakaoClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideKakaoApi(): KakaoApi {
        return KakaoClient.retrofit.create(KakaoApi::class.java)
    }
}