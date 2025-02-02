package com.knowmerce.app.ui.home.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.knowmerce.app.ui.shared.ContentItemList
import com.knowmerce.core.domain.model.SearchContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onSnackbar: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is FavoriteUiEffect.ShowToast -> onSnackbar(effect.message)
            }
        }
    }

    FavoriteScreen(
        modifier = modifier,
        searchContents = state.searchContents.collectAsLazyPagingItems(),
        removeFavorite = { viewModel.sendIntent(FavoriteUiIntent.RemoveFavorite(it)) }
    )
}

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    searchContents: LazyPagingItems<SearchContent>,
    removeFavorite: (SearchContent) -> Unit,
) {
    ContentItemList(
        modifier = modifier,
        searchContents = searchContents,
        isFavorite = { flowOf(false) },
        onLongClick = { removeFavorite(it) }
    )
}