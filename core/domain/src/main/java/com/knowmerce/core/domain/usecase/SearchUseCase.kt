package com.knowmerce.core.domain.usecase

import androidx.paging.PagingData
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(
        query: String,
        pageSize: Int = 20
    ): Flow<PagingData<SearchContent>> {
        return repository.search(
            query = query,
            pageSize = pageSize,
        )
    }
}