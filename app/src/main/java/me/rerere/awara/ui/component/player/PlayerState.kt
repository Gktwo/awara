package me.rerere.awara.ui.component.player

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

private const val TAG = "PlayerState"

@Composable
fun rememberPlayerState(): PlayerState {
    val context = LocalContext.current
    val state by remember {
        mutableStateOf(PlayerState())
    }
    DisposableEffect(context) {
        val player = ExoPlayer.Builder(context).build()
        val playbackListener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                state.state = playbackState
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                state.playing = isPlaying
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
                state.loading = isLoading
            }
        }
        player.addListener(playbackListener)
        state.controller = player
        Log.i(TAG, "rememberPlayerState: Init")
        onDispose {
            state.controller?.run {
                removeListener(playbackListener)
                release()
            }
            Log.i(TAG, "rememberPlayerState: Dispose")
        }
    }
    return state
}

class PlayerState {
    var controller: Player? by mutableStateOf(null)

    var playing by mutableStateOf(false)
    var state by mutableStateOf(Player.STATE_IDLE)
    var loading by mutableStateOf(false)

    fun play() {
        controller?.play()
    }

    fun pause() {
        controller?.pause()
    }

    fun seekTo(position: Long) {
        controller?.seekTo(position)
    }
}