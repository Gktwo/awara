package me.rerere.awara.ui.component.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    model: String,
    onClick: () -> Unit = {}
) {
    val state = rememberAsyncImagePainter(model = model)
    SkeletonBox(
        show = state.state is AsyncImagePainter.State.Loading,
        modifier = modifier
            .padding(4.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .size(32.dp)
    ) {
        Image(
            painter = state,
            contentDescription = "avatar",
            modifier = Modifier.fillMaxSize()
        )
    }
}