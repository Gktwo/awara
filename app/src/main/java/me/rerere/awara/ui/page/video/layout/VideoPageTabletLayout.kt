package me.rerere.awara.ui.page.video.layout

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.player.PlayerState
import me.rerere.awara.ui.page.video.VideoVM
import me.rerere.awara.ui.page.video.pager.VideoCommentPage
import me.rerere.awara.ui.page.video.pager.VideoOverviewPage

private const val TAG = "VideoPageTabletLayout"

@Composable
fun VideoPageTabletLayout(
    vm: VideoVM,
    state: PlayerState,
    player: @Composable () -> Unit
) {
    var offset by remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (consumed.y == 0f && available.y > 0f) {
                    offset = 0f
                } else {
                    offset += consumed.y
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier.animateContentSize()
                    ) {
                        if (offset != 0f && !state.playing) {
                            TopAppBar(
                                title = {
                                    Text(text = "视频详情")
                                },
                                navigationIcon = {
                                    BackButton()
                                }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .aspectRatio(16 / 9f)
                                    .fillMaxWidth()
                            ) {
                                player()
                            }
                        }
                    }
                    Box(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
                        VideoOverviewPage(vm)
                    }
                }
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                ) {
                    VideoCommentPage(vm)
                }
            }
        }
    }
}
