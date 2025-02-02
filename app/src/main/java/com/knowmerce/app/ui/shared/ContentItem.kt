package com.knowmerce.app.ui.shared

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.knowmerce.core.domain.model.SearchContent
import kotlinx.coroutines.flow.Flow

@Composable
fun ContentItemList(
    modifier: Modifier = Modifier,
    searchContents: LazyPagingItems<SearchContent>,
    isFavorite: (SearchContent) -> Flow<Boolean>,
    onClick: (SearchContent) -> Unit = {},
    onLongClick: (SearchContent) -> Unit = {},
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        state = rememberLazyStaggeredGridState(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(
            count = searchContents.itemCount,
            key = { index -> searchContents[index]?.thumbnailUrl ?: index }
        ) { item ->
            searchContents[item]?.let { content ->
                ContentItem(
                    modifier = Modifier
                        .animateItem(),
                    search = content,
                    isFavorite = isFavorite(content),
                    onClick = { onClick(content) },
                    onLongClick = { onLongClick(content) },
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentItem(
    modifier: Modifier = Modifier,
    search: SearchContent,
    isFavorite: Flow<Boolean>,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    var aspectRatio by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .padding(4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            ),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(search.thumbnailUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = search.title,
            placeholder = rememberVectorPainter(image = Icons.Default.ImageSearch),
            error = rememberVectorPainter(image = Icons.Default.ErrorOutline),
            onSuccess = { state ->
                val width = state.result.drawable.intrinsicWidth
                val height = state.result.drawable.intrinsicHeight
                if (width > 0 && height > 0) {
                    aspectRatio = width.toFloat() / height.toFloat()
                }
            }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ItemText(
                    text = search.displayedDatetime,
                )

                search.displayedPlayTime?.let {
                    ItemText(
                        text = it,
                    )
                }
            }

            ItemText(
                text = search.title,
            )
        }

        val isFav = isFavorite.collectAsStateWithLifecycle(false).value

        if (isFav) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Favorite",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ItemText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .background(
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(2.dp)
            )
            .padding(4.dp),
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}