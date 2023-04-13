package me.rerere.awara.ui.page.video.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.rerere.awara.ui.component.ext.excludeBottom
import me.rerere.awara.ui.page.video.VideoVM
import me.rerere.awara.ui.page.video.pager.VideoCommentPage
import me.rerere.awara.ui.page.video.pager.VideoOverviewPage

@Composable
fun VideoPageTabletLayout(vm: VideoVM, player: @Composable () -> Unit) {
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
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(3f)
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(16 / 9f)
                            .fillMaxWidth()
                    ) {
                        player()
                    }
                    VideoOverviewPage(vm)
                }

                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                ) {
                    VideoCommentPage(vm)
                }
            }
        }
    }
}