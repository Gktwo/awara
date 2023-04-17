package me.rerere.awara.ui.page.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Source
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.util.openUrl
import me.rerere.compose_setting.components.SettingItemCategory
import me.rerere.compose_setting.components.types.SettingBooleanItem
import me.rerere.compose_setting.components.types.SettingLinkItem
import me.rerere.compose_setting.components.types.SettingPickerItem
import me.rerere.compose_setting.preference.rememberBooleanPreference
import me.rerere.compose_setting.preference.rememberIntPreference

@Composable
fun SettingPage() {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(stringResource(R.string.setting_title))
                },
                navigationIcon = {
                    BackButton()
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item {
                SettingItemCategory(
                    title = {
                        Text("外观设置")
                    }
                ) {
                    val darkMode = rememberIntPreference(
                        key = "setting.dark_mode",
                        default = 0
                    )
                    val workMode = rememberBooleanPreference(
                        key = "setting.work_mode",
                        default = false
                    )
                    val dynamicColor = rememberBooleanPreference(
                        key = "setting.dynamic_color",
                        default = true
                    )
                    SettingPickerItem(
                        state = darkMode,
                        items = listOf(0, 1, 2),
                        itemLabel = { mode ->
                            Text(
                                text = when (mode) {
                                    0 -> "跟随系统"
                                    1 -> "浅色模式"
                                    2 -> "深色模式"
                                    else -> "未知"
                                }
                            )
                        },
                        title = {
                            Text("颜色模式")
                        },
                        icon = {
                            Icon(Icons.Outlined.LightMode, "Light/Dark Mode")
                        },
                        text = {
                            Text("设置应用的颜色模式")
                        }
                    )

                    SettingBooleanItem(
                        state = dynamicColor,
                        title = {
                            Text("动态颜色")
                        },
                        text = {
                            Text("是否根据视频封面动态调整颜色")
                        },
                        icon = {
                            Icon(Icons.Outlined.ColorLens,  null)
                        }
                    )

                    SettingBooleanItem(
                        state = workMode,
                        title = {
                            Text("工作模式")
                        },
                        text = {
                            Text("工作模式下，将会模糊视频封面")
                        },
                        icon = {
                            Icon(Icons.Outlined.HomeWork,  null)
                        }
                    )
                }
            }

            item {
                val autoPlay = rememberBooleanPreference(
                    key = "setting.auto_play",
                    default = true
                )
                SettingItemCategory(title = { Text("播放器设置") }) {
                    SettingBooleanItem(
                        state = autoPlay,
                        title = {
                            Text("自动播放")
                        },
                        text = {
                            Text("自动播放视频")
                        },
                        icon = {
                            Icon(Icons.Outlined.Replay,  null)
                        }
                    )
                }
            }

            item {
                SettingItemCategory(
                    title = {
                        Text("关于")
                    }
                ) {
                    SettingLinkItem(
                        title = {
                            Text("源码")
                        },
                        text = {
                            Text("Github")
                        },
                        icon = {
                            Icon(Icons.Outlined.Source, null)
                        },
                        onClick = {
                            context.openUrl("https://github.com/re-ovo/awara")
                        }
                    )
                }
            }
        }
    }
}