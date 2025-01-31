package com.knowmerce.core.domain.usecase

import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.model.SearchImage
import com.knowmerce.core.domain.model.SearchVideoClip
import com.knowmerce.core.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<SearchContent> {
        val images = repository.searchImage(
            query = query,
            page = 1,
            size = 10,
        )
        val videos = repository.searchVideoClip(
            query = query,
            page = 1,
            size = 10,
        )
        val searchResults = mutableListOf<SearchContent>()
        images.documents.map {
            searchResults.add(
                SearchImage(
                    thumbnailUrl = it.thumbnailUrl,
                    title = it.displaySiteName,
                    datetime = it.datetime,
                )
            )
        }
        videos.documents.map {
            searchResults.add(
                SearchVideoClip(
                    thumbnailUrl = it.thumbnail,
                    title = it.title,
                    datetime = it.datetime,
                    playTime = it.playTime,
                )
            )
        }
        searchResults.sortByDescending { it.datetime }
        return searchResults
    }
}