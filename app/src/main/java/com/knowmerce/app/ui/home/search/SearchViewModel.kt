package com.knowmerce.app.ui.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data class Ready(
        val searchContents: Flow<PagingData<SearchContent>>,
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
    private val searchUseCase: SearchUseCase,
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
                if (query.isBlank()) {
                    _state.value = SearchUiState.Loading
                    return@launch
                }

                val searchContents = searchUseCase(query)

                _state.value = SearchUiState.Ready(
                    searchContents = searchContents.cachedIn(viewModelScope)
                )
            } catch (e: Exception) {
                _state.value = SearchUiState.Loading
                _effect.emit(SearchUiEffect.ShowToast(e.message ?: "Unknown error"))
            }
        }
    }
}