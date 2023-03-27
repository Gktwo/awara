package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import me.rerere.awara.data.entity.Media
import me.rerere.awara.data.entity.thumbnailUrl
import me.rerere.awara.ui.component.common.SkeletonBox

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    media: Media
) {
    Card(
        modifier = modifier,
        onClick = {}
    ) {
        Column {
            val painter = rememberAsyncImagePainter(
                model = media.thumbnailUrl()
            )
            SkeletonBox(
                show = painter.state is AsyncImagePainter.State.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Media Cover",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.padding(
                    horizontal = 6.dp,
                    vertical = 4.dp
                )
            ) {
                Text(
                    text = media.title.trim(),
                    maxLines = 2,
                    style = MaterialTheme.typography.labelLarge
                )

                Row {
                    Text(
                        text = media.user.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = LocalContentColor.current.copy(alpha = 0.75f),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = LocalContentColor.current.copy(alpha = 0.75f),
                        modifier = Modifier.height(
                            16.dp
                        )
                    )
                    Text(
                        text = media.numLikes.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = LocalContentColor.current.copy(alpha = 0.75f)
                    )
                }
            }
        }
    }
}