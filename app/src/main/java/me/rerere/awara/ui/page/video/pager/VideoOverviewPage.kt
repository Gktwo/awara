package me.rerere.awara.ui.page.video.pager

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.PlaylistAdd
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.rerere.awara.data.entity.Video
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.common.ButtonType
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.ext.DynamicStaggeredGridCells
import me.rerere.awara.ui.component.ext.plus
import me.rerere.awara.ui.component.iwara.Avatar
import me.rerere.awara.ui.component.iwara.MediaCard
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.ui.component.iwara.TagRow
import me.rerere.awara.ui.page.video.VideoVM
import me.rerere.awara.util.openUrl
import me.rerere.awara.util.toLocalDateTimeString

@Composable
fun VideoOverviewPage(vm: VideoVM) {
    val state = vm.state
    Spin(
        show = state.loading,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            columns = DynamicStaggeredGridCells(),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(8.dp) + WindowInsets.navigationBars.asPaddingValues()
        ) {
            state.video?.let {
                item(span = StaggeredGridItemSpan.FullLine) {
                    VideoInfoCard(
                        video = it,
                        vm = vm
                    )
                }
                item(span = StaggeredGridItemSpan.FullLine) {
                    AuthorCard(
                        video = it
                    )
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    TagRow(
                        tags = state.video.tags,
                    )
                }

                items(state.relatedVideos) {
                    MediaCard(media = it)
                }
            }
        }
    }
}

@Composable
private fun VideoInfoCard(video: Video, vm: VideoVM) {
    val (expand, setExpand) = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = video.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (expand) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "More",
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .clickable {
                                setExpand(!expand)
                            }
                    )
                }

                if (!video.body.isNullOrBlank() && expand) {
                    RichText(
                        text = video.body.trim(),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        onLinkClick = {
                            context.openUrl(it)
                        }
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

                var showPlaylistSheet by remember {
                    mutableStateOf(false)
                }
                IconButton(
                    onClick = {
                        showPlaylistSheet = true
                    }
                ) {
                    Icon(Icons.Outlined.PlaylistAdd, null)
                }
                if(showPlaylistSheet) {
                    PlaylistSheet(
                        vm = vm,
                        onDismissRequest = {showPlaylistSheet = false}
                    )
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
    val router = LocalRouterProvider.current
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            router.navigate("user/${video.user.username}")
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        ) {
            Avatar(
                user = video.user,
                modifier = Modifier.size(32.dp)
            )
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
        }
    }
}