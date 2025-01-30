@file:OptIn(ExperimentalMaterial3Api::class)

package com.knowmerce.app.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        ),
        sheetMaxWidth = LocalConfiguration.current.screenWidthDp.dp,
        sheetPeekHeight = 100.dp,
        sheetDragHandle = null,
        sheetContainerColor = Color.Transparent,
        sheetShape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetContent = {
            SearchScreen()
        }
    ) {

    }


}

@Composable
fun SearchScreen(

) {
    var searchQuery by remember { mutableStateOf("") }
    val items = listOf("Apple", "Banana", "Cherry", "Date", "Grape", "Lemon")
    val filteredItems = items.filter { it.contains(searchQuery, ignoreCase = true) }

    Column {
        SearchBar(query = searchQuery, onQueryChange = { searchQuery = it })

        LazyColumn {
            items(filteredItems) { item ->
                Text(text = item, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
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
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}