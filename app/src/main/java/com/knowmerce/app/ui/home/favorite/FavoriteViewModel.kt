package com.knowmerce.app.ui.home.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.knowmerce.app.R
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.usecase.AddFavoriteUseCase
import com.knowmerce.core.domain.usecase.GetFavoritesUseCase
import com.knowmerce.core.domain.usecase.IsFavoriteUseCase
import com.knowmerce.core.domain.usecase.RemoveAllFavoritesUseCase
import com.knowmerce.core.domain.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoriteUiState(
    val searchContents: Flow<PagingData<SearchContent>>,
)

sealed interface FavoriteUiIntent {
    data class AddFavorite(val searchContent: SearchContent) : FavoriteUiIntent
    data class RemoveFavorite(val searchContent: SearchContent) : FavoriteUiIntent
    data object RemoveAllFavorites : FavoriteUiIntent
}

sealed interface FavoriteUiEffect {
    data class ShowToast(val message: String) : FavoriteUiEffect
    data class ShowToastId(val messageId: Int) : FavoriteUiEffect
}

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val removeAllFavoritesUseCase: RemoveAllFavoritesUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<FavoriteUiState> =
        MutableStateFlow(FavoriteUiState(searchContents = getFavoritesUseCase()))
    val state: MutableStateFlow<FavoriteUiState> = _state

    private val _intent = MutableSharedFlow<FavoriteUiIntent>(extraBufferCapacity = 1)

    private val _effect = MutableSharedFlow<FavoriteUiEffect>(extraBufferCapacity = 1)
    val effect: MutableSharedFlow<FavoriteUiEffect> = _effect

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            _intent.collectLatest { intent ->
                when (intent) {
                    is FavoriteUiIntent.AddFavorite -> addFavorite(intent.searchContent)
                    is FavoriteUiIntent.RemoveFavorite -> removeFavorite(intent.searchContent)
                    is FavoriteUiIntent.RemoveAllFavorites -> removeAllFavorites()
                }
            }
        }
    }

    fun sendIntent(intent: FavoriteUiIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun addFavorite(searchContent: SearchContent) {
        viewModelScope.launch {
            try {
                addFavoriteUseCase(searchContent)
                _effect.emit(FavoriteUiEffect.ShowToastId(R.string.my_storage_added))
            } catch (e: Exception) {
                _effect.emit(FavoriteUiEffect.ShowToast(e.message ?: ""))
            }
        }
    }

    private fun removeFavorite(searchContent: SearchContent) {
        viewModelScope.launch {
            try {
                removeFavoriteUseCase(searchContent)
                _effect.emit(FavoriteUiEffect.ShowToastId(R.string.my_storage_delete))
            } catch (e: Exception) {
                _effect.emit(FavoriteUiEffect.ShowToast(e.message ?: ""))
            }
        }
    }

    private fun removeAllFavorites() {
        viewModelScope.launch {
            try {
                removeAllFavoritesUseCase()
                _effect.emit(FavoriteUiEffect.ShowToastId(R.string.my_storage_empty))
            } catch (e: Exception) {
                _effect.emit(FavoriteUiEffect.ShowToast(e.message ?: ""))
            }
        }
    }

    fun isFavorite(searchContent: SearchContent): Flow<Boolean> {
        return isFavoriteUseCase(searchContent)
    }
}