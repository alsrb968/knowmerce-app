package com.knowmerce.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.knowmerce.app.R
import com.knowmerce.app.ui.home.favorite.FavoriteScreen
import com.knowmerce.app.ui.home.search.SearchScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val statusBarHeight = WindowInsets.statusBars.getTop(Density(1f)).dp
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    var topBarHeight by remember { mutableStateOf(0.dp) }

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = true
    )

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    fun onSnackbar(message: String) {
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message)
        }
    }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(sheetState.currentValue) {
        when (sheetState.currentValue) {
            SheetValue.PartiallyExpanded -> focusManager.clearFocus()
            else -> {}
        }
    }

    BottomSheetScaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .onSizeChanged {
                        topBarHeight = it.height.dp
                    }
                    .clickable {
                        scope.launch {
                            focusManager.clearFocus()
                            sheetState.partialExpand()
                        }
                    },
                title = {
                    Text(text = stringResource(R.string.my_storage))
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Inventory,
                        contentDescription = stringResource(R.string.search),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                            .clip(RoundedCornerShape(50))
                    )
                },
            )
        },
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .size(width = 40.dp, height = 4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Gray)
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        ),
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetContent = {
            SearchScreen(
                modifier = Modifier
                    .heightIn(
                        max = screenHeight - topBarHeight + statusBarHeight
                    ),
                onSnackbar = { message ->
                    onSnackbar(message)
                },
                onBottomSheetExpand = { expanded ->
                    scope.launch {
                        if (expanded) {
                            sheetState.expand()
                        } else {
                            sheetState.partialExpand()
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        FavoriteScreen(
            modifier = Modifier,
            onSnackbar = { message ->
                onSnackbar(message)
            }
        )
    }
}