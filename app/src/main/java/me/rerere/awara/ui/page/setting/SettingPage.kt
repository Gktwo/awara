package me.rerere.awara.ui.page.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LightMode
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
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.compose_setting.components.SettingItemCategory
import me.rerere.compose_setting.components.types.SettingLinkItem
import me.rerere.compose_setting.components.types.SettingPickerItem
import me.rerere.compose_setting.preference.rememberIntPreference

@Composable
fun SettingPage() {
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
                            Icon(Icons.Outlined.Source, "Source Code")
                        },
                        onClick = { /*TODO*/ }
                    )
                }
            }
        }
    }
}