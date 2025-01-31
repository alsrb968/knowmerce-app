package com.knowmerce.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.knowmerce.app.ui.home.search.SearchScreen
import kotlinx.coroutines.launch
import timber.log.Timber

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

    BottomSheetScaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(100.dp)
                    .onSizeChanged {
                        topBarHeight = it.height.dp
                        Timber.i("screenHeight: $screenHeight, topBarHeight: $topBarHeight")
                    },
                title = {
                    Text(text = "즐겨찾기")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
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
                    )
                ,
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
        Button(
            onClick = {
                onSnackbar("즐겨찾기에 추가되었습니다.")
            }
        ) { }
    }
}