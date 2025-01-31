package com.knowmerce.core.data.di

import com.knowmerce.core.domain.repository.SearchRepository
import com.knowmerce.core.domain.usecase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideSearchUseCase(repository: SearchRepository) =
        SearchUseCase(repository)
}