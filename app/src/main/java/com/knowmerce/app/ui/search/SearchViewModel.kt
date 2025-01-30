package com.knowmerce.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowmerce.core.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class SearchUiModel(
    val thumbnailUrl: String,
    val title: String,
    val datetime: String,
)

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data class Ready(
        val searchResults: List<SearchUiModel>,
    ) : SearchUiState
}

sealed interface SearchUiIntent {
    data class Search(val query: String) : SearchUiIntent
}

sealed interface SearchUiEffect {
    data class ShowToast(val message: String) : SearchUiEffect
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.Loading)
    val state: StateFlow<SearchUiState> = _state

    private val _intent = MutableSharedFlow<SearchUiIntent>(extraBufferCapacity = 1)

    private val _effect = MutableSharedFlow<SearchUiEffect>(extraBufferCapacity = 1)
    val effect: SharedFlow<SearchUiEffect> = _effect

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            _intent.collectLatest { intent ->
                when (intent) {
                    is SearchUiIntent.Search -> search(intent.query)
                }
            }
        }
    }

    fun sendIntent(intent: SearchUiIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            try {
                Timber.i("search: $query")
                val images = searchRepository.searchImage(
                    query = query,
                    page = 1,
                    size = 10,
                )
                val videos = searchRepository.searchVideoClip(
                    query = query,
                    page = 1,
                    size = 10,
                )
                val searchResults = mutableListOf<SearchUiModel>()
                images.documents.map {
                    searchResults.add(
                        SearchUiModel(
                            thumbnailUrl = it.thumbnailUrl,
                            title = it.displaySiteName,
                            datetime = it.datetime,
                        )
                    )
                }
                videos.documents.map {
                    searchResults.add(
                        SearchUiModel(
                            thumbnailUrl = it.thumbnail,
                            title = it.title,
                            datetime = it.datetime,
                        )
                    )
                }
                searchResults.sortByDescending { it.datetime }
                _state.value = SearchUiState.Ready(
                    searchResults = searchResults
                )
            } catch (e: Exception) {
                _effect.emit(SearchUiEffect.ShowToast(e.localizedMessage ?: "Unknown error"))
            }
        }
    }
}