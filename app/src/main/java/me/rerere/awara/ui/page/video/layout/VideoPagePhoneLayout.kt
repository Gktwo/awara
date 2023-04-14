package me.rerere.awara.ui.page.video.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.BetterTabBar
import me.rerere.awara.ui.component.ext.excludeBottom
import me.rerere.awara.ui.component.player.PlayerState
import me.rerere.awara.ui.page.video.VideoVM
import me.rerere.awara.ui.page.video.pager.VideoCommentPage
import me.rerere.awara.ui.page.video.pager.VideoOverviewPage

@Composable
fun VideoPagePhoneLayout(vm: VideoVM, state: PlayerState, player: @Composable () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
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
    Scaffold(
        topBar = {
            if (offset != 0f && !state.playing) {
                TopAppBar(
                    title = {
                        Text(text = "视频详情")
                    },
                    navigationIcon = {
                        BackButton()
                    },
                    modifier = Modifier
                        .background(Color.Black)
                        .statusBarsPadding()
                )
            } else {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                ) {
                    player()
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding.excludeBottom())
                .fillMaxSize()
        ) {
            BetterTabBar(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                    text = {
                        Text("简介")
                    },
                )

                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("评论")
                            Text(
                                text = "${vm.state.video?.numComments ?: 0}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                )
            }
            HorizontalPager(
                pageCount = 2,
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .weight(1f)
                    .fillMaxWidth(),
                state = pagerState,
            ) {
                when (it) {
                    0 -> VideoOverviewPage(vm)
                    1 -> VideoCommentPage(vm)
                }
            }
        }
    }
}