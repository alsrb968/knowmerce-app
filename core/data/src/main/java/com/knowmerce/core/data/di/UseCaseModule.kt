package com.knowmerce.core.data.di

import com.knowmerce.core.domain.repository.SearchRepository
import com.knowmerce.core.domain.usecase.AddFavoriteUseCase
import com.knowmerce.core.domain.usecase.GetFavoritesUseCase
import com.knowmerce.core.domain.usecase.IsFavoriteUseCase
import com.knowmerce.core.domain.usecase.RemoveAllFavoritesUseCase
import com.knowmerce.core.domain.usecase.RemoveFavoriteUseCase
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

    @Provides
    @Singleton
    fun provideAddFavoriteUseCase(repository: SearchRepository) =
        AddFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveFavoriteUseCase(repository: SearchRepository) =
        RemoveFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveAllFavoritesUseCase(repository: SearchRepository) =
        RemoveAllFavoritesUseCase(repository)

    @Provides
    @Singleton
    fun provideIsFavoriteUseCase(repository: SearchRepository) =
        IsFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoritesUseCase(repository: SearchRepository) =
        GetFavoritesUseCase(repository)
}