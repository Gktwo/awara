package me.rerere.awara.ui.page.video.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.rerere.awara.ui.page.video.VideoVM
import me.rerere.awara.ui.page.video.pager.VideoOverviewPage

@Composable
fun VideoPagePhoneLayout(vm: VideoVM, player: @Composable () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                    text = {
                        Text("简介")
                    }
                )

                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                    text = {
                        Text("评论")
                    }
                )
            }
            HorizontalPager(
                pageCount = 2,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = pagerState
            ) {
                when (it) {
                    0 -> VideoOverviewPage(vm)
                    1 -> {}
                }
            }
        }
    }
}