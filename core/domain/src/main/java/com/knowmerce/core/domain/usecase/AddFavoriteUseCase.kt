package com.knowmerce.core.domain.usecase

import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.repository.SearchRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchContent: SearchContent) {
        repository.addFavorite(searchContent)
    }
}