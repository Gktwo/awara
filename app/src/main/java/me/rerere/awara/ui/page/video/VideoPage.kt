package me.rerere.awara.ui.page.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import me.rerere.awara.ui.component.player.Player
import me.rerere.awara.ui.component.player.PlayerBase
import me.rerere.awara.ui.component.player.PlayerScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoPage(vm: VideoVM = koinViewModel()) {
    val state = rememberPlayerState()
    var fullscreen by remember {
        mutableStateOf(false)
    }
    PlayerScaffold(
        fullscreen = fullscreen,
        player = {
            Player(
                state = state,
                modifier = Modifier.fillMaxSize(),
                actions = {
                    IconButton(
                        onClick = {
                            fullscreen = !fullscreen
                        }
                    ) {
                        Icon(Icons.Outlined.Fullscreen, "Fullscreen")
                    }
                }
            )
        }
    ) { player ->
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                var count by remember {
                    mutableStateOf(0)
                }

                Box(modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()) {
                    player()
                }

                Button(onClick = { count++ }) {
                    Text("C: $count")
                }

                Text("playing: ${state.playing} state: ${state.state} loading: ${state.loading}")

                Button(onClick = {
                    state.player.run {
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
}