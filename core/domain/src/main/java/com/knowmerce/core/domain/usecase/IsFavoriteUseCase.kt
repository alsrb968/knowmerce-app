package com.knowmerce.core.domain.usecase

import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(searchContent: SearchContent): Flow<Boolean> {
        return repository.isFavorite(searchContent)
    }
}