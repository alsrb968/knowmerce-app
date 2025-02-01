package com.knowmerce.core.domain.usecase

import com.knowmerce.core.domain.repository.SearchRepository
import javax.inject.Inject

class RemoveAllFavoritesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke() {
        repository.removeAllFavorites()
    }
}