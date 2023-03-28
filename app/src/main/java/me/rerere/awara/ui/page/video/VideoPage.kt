package me.rerere.awara.ui.page.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.media3.common.MediaItem
import me.rerere.awara.ui.component.player.rememberPlayerState
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.player.PlayerBase
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoPage(vm: VideoVM = koinViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(vm.id)
                },
                navigationIcon = {
                    BackButton()
                }
            )
        }
    ) {
        Column {
            val state = rememberPlayerState()
            var count by remember {
                mutableStateOf(0)
            }
            PlayerBase(
                state = state,
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .aspectRatio(16/9f)
            )

            Button(onClick = { count++ }) {
                Text("C: $count")
            }

            Text("playing: ${state.playing} state: ${state.state} loading: ${state.loading}")

            Button(onClick = {
                state.controller?.run {
                    playWhenReady = true
                    setMediaItem(
                        MediaItem.fromUri("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")
                    )
                    prepare()
                    play()
                }
            }) {
               Text("Play")
            }
        }
    }
}