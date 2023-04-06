package me.rerere.awara.ui.page.search

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.ui.hooks.rememberFullScreenState
import me.rerere.awara.ui.hooks.rememberRequestedScreenOrientation

@Composable
fun SearchPage() {
    val router = LocalRouterProvider.current
    val fullScreenState = rememberFullScreenState()
    val message = LocalMessageProvider.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Search")
                },
                navigationIcon = {
                    BackButton()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Button(
                onClick = {
                    if (fullScreenState.isFullScreen) {
                        fullScreenState.exitFullScreen()
                    } else {
                        fullScreenState.enterFullScreen()
                    }
                }
            ) {
                Text("F: ${fullScreenState.isFullScreen}")
            }

            val orientation = rememberRequestedScreenOrientation()
            Button(
                onClick = {
                    orientation.value = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            ) {
                Text("Orientation: ${orientation.value}")
            }

            Button(onClick = {
                message.warning {
                    Text("Warning")
                }
            }) {
                Text("Msg")
            }

            RichText(
                text = """
                # 测试
                awa
                abc[aa](https://bb.com)
                **aa**
            """.trimIndent(),
                onLinkClick = {
                    message.info {
                        Text(it)
                    }
                },
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
