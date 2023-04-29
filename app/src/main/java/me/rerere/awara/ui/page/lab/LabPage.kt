package me.rerere.awara.ui.page.lab

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.ext.plus
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.ui.component.iwara.param.FilterValue
import me.rerere.awara.ui.component.iwara.param.filter.DateFilter
import me.rerere.awara.ui.component.iwara.param.filter.RatingFilter
import me.rerere.awara.ui.component.iwara.param.filter.TagFilter
import me.rerere.awara.ui.component.iwara.param.toParams
import me.rerere.awara.ui.component.player.DlnaSelector
import me.rerere.awara.ui.component.player.rememberDlnaCastState
import me.rerere.awara.ui.hooks.rememberFullScreenState
import me.rerere.awara.ui.hooks.rememberRequestedScreenOrientation

@Composable
fun LabPage() {
    val router = LocalRouterProvider.current
    val fullScreenState = rememberFullScreenState()
    val message = LocalMessageProvider.current
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lab") },
                navigationIcon = {
                    BackButton()
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = it + PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                LabItem {
                    Column {
                        val filters = remember {
                            mutableStateListOf<FilterValue>()
                        }
                        Text(
                            text = filters.toParams().toList().joinToString(", ")
                        )

                        DateFilter(
                            values = filters,
                            onValueAdd = { filters.add(it) },
                            onValueRemove = { filters.remove(it) }
                        )

                        RatingFilter(
                            values = filters,
                            onValueAdd = { filters.add(it) },
                            onValueRemove = { filters.remove(it) }
                        )

                        TagFilter(
                            values = filters,
                            onValueAdd = { filters.add(it) },
                            onValueRemove = { filters.remove(it) }
                        )
                    }
                }
            }

            item {
                // Crash
                LabItem {
                    Button(onClick = {
                        throw RuntimeException("Crash")
                    }) {
                        Text("Crash")
                    }
                }
            }

            item {
                // Counter
                var counter by remember { mutableStateOf(0) }
                LabItem {
                    Button(onClick = {
                        counter++
                    }) {
                        Text(counter.toString())
                    }
                }
            }

            item {
                // FullScreen
                LabItem {
                    Button(
                        onClick = {
                            if (fullScreenState.isFullScreen) {
                                fullScreenState.exitFullScreen()
                            } else {
                                fullScreenState.enterFullScreen()
                            }
                        }
                    ) {
                        Text("Full Screen: ${fullScreenState.isFullScreen}")
                    }
                }
            }

            item {
                // Orientation
                val orientation = rememberRequestedScreenOrientation()
                LabItem {
                    Button(
                        onClick = {
                            orientation.value = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }
                    ) {
                        Text("Orientation: ${orientation.value}")
                    }
                }
            }

            item {
                // RichText
                LabItem {
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

            item {
                // DLNA
                val state = rememberDlnaCastState()
                LabItem {
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
                }
            }
        }
    }
}