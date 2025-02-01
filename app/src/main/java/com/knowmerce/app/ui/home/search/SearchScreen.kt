package com.knowmerce.app.ui.home.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.knowmerce.core.domain.model.SearchContent
import kotlinx.coroutines.FlowPreview
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
    when (val s = state) {
        is SearchUiState.Loading -> {
            SearchScreen(
                modifier = modifier,
                searchResults = emptyList(),
                onSearch = { query ->
                    viewModel.sendIntent(SearchUiIntent.Search(query))
                },
                onFocusChanged = onBottomSheetExpand
            )
        }
        is SearchUiState.Ready -> {
            SearchScreen(
                modifier = modifier,
                searchResults = s.searchResults,
                onSearch = { query ->
                    viewModel.sendIntent(SearchUiIntent.Search(query))
                },
                onFocusChanged = onBottomSheetExpand
            )
        }
    }

}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchResults: List<SearchContent>,
    onSearch: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("카리나") }

    Column(
        modifier = modifier,
    ) {
        SearchBar(
            modifier = Modifier,
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = onSearch,
            onFocusChanged = onFocusChanged
        )

        LazyVerticalStaggeredGrid(
            modifier = Modifier
//                .fillMaxSize()
            ,
            columns = StaggeredGridCells.Fixed(2),
            state = rememberLazyStaggeredGridState(),
            contentPadding = PaddingValues(4.dp),
        ) {
            items(searchResults.size) { item ->
                SearchItem(
                    search = searchResults[item],
                    onClick = { /* TODO */ }
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
            .debounce(500) // 500ms 대기 후 마지막 입력값 사용
            .collectLatest { text ->
                if (text.isNotEmpty()) {
                    onSearch(text)
                }
            }
    }

    TextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
            searchFlow.value = it // Flow 업데이트
        },
        placeholder = { Text("검색어를 입력하세요") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "검색 아이콘")
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "입력 삭제")
                }
            }
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
//            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
//            .padding(horizontal = 16.dp, vertical = 8.dp)
            .onFocusChanged { onFocusChanged(it.isFocused) }
    )
}