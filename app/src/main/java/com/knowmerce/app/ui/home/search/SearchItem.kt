package com.knowmerce.app.ui.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.knowmerce.core.domain.model.SearchContent
import com.knowmerce.core.domain.model.SearchVideoClip
import com.knowmerce.core.domain.model.displayedDatetime

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    search: SearchContent,
    onClick: (() -> Unit)? = null,
) {
    var aspectRatio by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .padding(4.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = search.thumbnailUrl,
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
            ItemText(
                text = search.displayedDatetime,
            )

            if (search is SearchVideoClip) {
                ItemText(
                    text = search.displayedPlayTime,
                )
            }

            ItemText(
                text = search.title,
            )
        }

        if (onClick != null) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Keep",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
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