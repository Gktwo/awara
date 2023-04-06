package me.rerere.awara.ui.page.video.pager

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.PlaylistAdd
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.rerere.awara.data.entity.Video
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.common.ButtonType
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.iwara.Avatar
import me.rerere.awara.ui.component.iwara.MediaCard
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.ui.page.video.VideoVM
import me.rerere.awara.util.toLocalDateTimeString

@Composable
fun VideoOverviewPage(vm: VideoVM) {
    val state = vm.state
    Spin(
        show = state.loading,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            state.video?.let {
                VideoInfoCard(
                    video = it,
                    vm = vm
                )

                AuthorCard(
                    video = it
                )

                MoreLikeThis(
                    vm = vm
                )
            }
        }
    }
}

@Composable
private fun VideoInfoCard(video: Video, vm: VideoVM) {
    val (expand, setExpand) = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .animateContentSize()
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = video.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1
                    )

                    if (!video.body.isNullOrBlank()) {
                        RichText(
                            text = video.body.trim(),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = if (expand) Int.MAX_VALUE else 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                IconButton(
                    onClick = { setExpand(!expand) }
                ) {
                    Icon(
                        imageVector = if (expand) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "More"
                    )
                }
            }

            ProvideTextStyle(MaterialTheme.typography.labelSmall) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = video.createdAt.toLocalDateTimeString()
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "喜欢: " + video.numLikes.toString()
                    )

                    Text(
                        text = "播放: " + video.numViews.toString(),
                    )
                }
            }

            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Download, null)
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Share, null)
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Translate, null)
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.PlaylistAdd, null)
                }

                Button(
                    onClick = {
                        vm.likeOrUnlike()
                    },
                    type = if (video.liked) ButtonType.Outlined else ButtonType.Default,
                    loading = vm.state.likeLoading
                ) {
                    Text(if (video.liked) "取消喜欢" else "喜欢")
                }
            }
        }
    }
}

@Composable
private fun AuthorCard(video: Video) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        ) {
            Avatar(user = video.user)
            Column {
                Text(
                    text = video.user.name,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "@" + video.user.username,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = if (video.user.following) "已关注" else "关注"
                )
            }
        }
    }
}

@Composable
private fun MoreLikeThis(vm: VideoVM) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "更多类似视频",
            style = MaterialTheme.typography.titleSmall
        )

        // 2 columns
        vm.state.relatedVideos.chunked(2).forEach { chunk ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chunk.forEach {
                    MediaCard(media = it, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
