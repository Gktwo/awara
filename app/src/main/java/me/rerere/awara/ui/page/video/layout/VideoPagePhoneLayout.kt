package me.rerere.awara.ui.page.video.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.rerere.awara.ui.page.video.VideoVM
import me.rerere.awara.ui.page.video.pager.VideoOverviewPage

@Composable
fun VideoPagePhoneLayout(vm: VideoVM, player: @Composable () -> Unit) {
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
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            VideoOverviewPage(vm)
        }
    }
}