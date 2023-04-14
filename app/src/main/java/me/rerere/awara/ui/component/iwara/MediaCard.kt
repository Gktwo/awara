package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import me.rerere.awara.data.entity.Image
import me.rerere.awara.data.entity.Media
import me.rerere.awara.data.entity.Video
import me.rerere.awara.data.entity.thumbnailUrl
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.SkeletonBox
import me.rerere.compose_setting.preference.rememberBooleanPreference

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    media: Media
) {
    val router = LocalRouterProvider.current
    val workMode by rememberBooleanPreference(
        key = "setting.work_mode",
        default = false
    )
    Card(
        modifier = modifier,
        onClick = {
            when (media) {
                is Video -> router.navigate("video/${media.id}")
                is Image -> router.navigate("image/${media.id}")
            }
        }
    ) {
        Column {
            val painter = rememberAsyncImagePainter(
                model = media.thumbnailUrl()
            )
            SkeletonBox(
                show = painter.state is AsyncImagePainter.State.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(220f / 160f),
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Media Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (workMode) Modifier.blur(8.dp) else Modifier)
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