package com.knowmerce.app.ui.home.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.knowmerce.app.R
import com.knowmerce.app.ui.shared.ContentItemList
import com.knowmerce.core.domain.model.SearchContent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onSnackbar: (String) -> Unit,
    onBottomSheetExpand: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SearchUiEffect.ShowToast -> onSnackbar(effect.message)
                is SearchUiEffect.ShowToastId -> onSnackbar(context.getString(effect.messageId))
            }
        }
    }

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
    ) {
        SearchBar(
            modifier = Modifier,
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { query ->
                viewModel.sendIntent(SearchUiIntent.Search(query))
            },
            onFocusChanged = onBottomSheetExpand
        )

        when (val s = state) {
            is SearchUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is SearchUiState.Ready -> {
                SearchScreen(
                    modifier = Modifier.fillMaxSize(),
                    searchContents = s.searchContents.collectAsLazyPagingItems(),
                    isFavorite = { viewModel.isFavorite(it) },
                    toggleFavorite = { viewModel.sendIntent(SearchUiIntent.ToggleFavorite(it)) }
                )
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchContents: LazyPagingItems<SearchContent>,
    isFavorite: (SearchContent) -> Flow<Boolean>,
    toggleFavorite: (SearchContent) -> Unit,
) {
    ContentItemList(
        modifier = modifier,
        searchContents = searchContents,
        isFavorite = isFavorite,
        onClick = { toggleFavorite(it) }
    )
}

@OptIn(FlowPreview::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    val searchFlow = remember { MutableStateFlow(query) }

    LaunchedEffect(Unit) {
        searchFlow
            .debounce(500)
            .collectLatest { text ->
                onSearch(text)
            }
    }

    TextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
            searchFlow.value = it
        },
        placeholder = { Text(stringResource(R.string.search_hint)) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search_icon))
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                    searchFlow.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(R.string.delete_input))
                }
            }
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .onFocusChanged { onFocusChanged(it.isFocused) }
    )
}