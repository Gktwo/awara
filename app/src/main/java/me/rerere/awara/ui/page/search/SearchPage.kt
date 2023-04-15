package me.rerere.awara.ui.page.search

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.BetterTabBar
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.ui.component.player.DlnaSelector
import me.rerere.awara.ui.component.player.rememberDlnaCastState
import me.rerere.awara.ui.hooks.rememberFullScreenState
import me.rerere.awara.ui.hooks.rememberRequestedScreenOrientation
import me.rerere.compose_setting.preference.mmkvPreference

@Composable
fun SearchPage() {
    val router = LocalRouterProvider.current
    val fullScreenState = rememberFullScreenState()
    val message = LocalMessageProvider.current
    val scope = rememberCoroutineScope()
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
            // FullScreen
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

            // Orientation
            val orientation = rememberRequestedScreenOrientation()
            Button(
                onClick = {
                    orientation.value = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            ) {
                Text("Orientation: ${orientation.value}")
            }

            // DLNA
            val state = rememberDlnaCastState()
            state.deviceList.forEach {
                Text(it.friendlyName)
            }

            val showSelector = remember { mutableStateOf(false) }
            Button(onClick = {
                showSelector.value = true
            }) {
                Text("Dlna Selector")
            }
            DlnaSelector(
                show = showSelector.value,
                onDismiss = { showSelector.value = false },
                state = state,
                onDeviceSelected = {
                    message.info {
                        Text(it.friendlyName)
                    }
                }
            )

            // Counter
            var counter by remember { mutableStateOf(0) }
            Button(onClick = {
                counter++
            }) {
                Text(counter.toString())
            }

            // Settings
            Button(onClick = {
                message.info {
                    Text(
                        text = mmkvPreference.getString("setting.player_quality","")!!
                    )
                }
            }) {
                Text("Dump Settings")
            }

            // Crash
            Button(onClick = {
                throw RuntimeException("Crash")
            }) {
                Text("Crash")
            }

            // RichText
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

            BetterTabBar(selectedTabIndex = 0) {
                Tab(selected = true, onClick = { /*TODO*/ }) {
                    Text("测试")
                }
            }
        }
    }
}
