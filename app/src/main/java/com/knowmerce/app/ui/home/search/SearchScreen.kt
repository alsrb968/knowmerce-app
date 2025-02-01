package com.knowmerce.app.ui.home.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
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
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SearchUiEffect.ShowToast -> onSnackbar(effect.message)
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
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        state = rememberLazyStaggeredGridState(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(searchContents.itemCount) { item ->
            searchContents[item]?.let { content ->
                SearchItem(
                    search = content,
                    isFavorite = isFavorite(content),
                    onClick = { toggleFavorite(content) }
                )
            }
        }
    }
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
        placeholder = { Text("검색어를 입력하세요") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "검색 아이콘")
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                    searchFlow.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "입력 삭제")
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