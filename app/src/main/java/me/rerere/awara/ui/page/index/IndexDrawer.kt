package me.rerere.awara.ui.page.index

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.FeaturedPlayList
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.iwara.Avatar
import me.rerere.awara.ui.stores.LocalUserStore
import me.rerere.awara.ui.stores.UserStoreAction
import me.rerere.awara.ui.stores.collectAsState

@Composable
fun ColumnScope.IndexDrawer() {
    val userStore = LocalUserStore.current
    val userState = userStore.collectAsState()
    val router = LocalRouterProvider.current
    val message = LocalMessageProvider.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Avatar(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            user = userState.user,
            onClick = {
                router.navigate("login")
            }
        )
        Column {
            // User nick name
            Text(
                text = userState.user?.name ?: "未登录",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            // ID
            if (userState.user?.username != null) {
                Text(
                    text = "@${userState.user.username}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                userStore(UserStoreAction.Logout)
                message.info {
                    Text("已登出")
                }
            }
        ) {
            Icon(Icons.Outlined.ExitToApp, "Logout")
        }
    }


    DrawerItem(
        icon = {
            Icon(Icons.Outlined.FeaturedPlayList, "PlayList")
        },
        label = {
            Text("播单")
        },
        onClick = {}
    )

    DrawerItem(
        icon = {
            Icon(Icons.Outlined.Download, "Downloads")
        },
        label = {
            Text("下载")
        },
        onClick = {}
    )

    DrawerItem(
        icon = {
            Icon(Icons.Outlined.History, "History")
        },
        label = {
            Text("历史记录")
        },
        onClick = {
            router.navigate("history")
        }
    )

    DrawerItem(
        icon = {
            Icon(Icons.Outlined.Settings, "Settings")
        },
        label = {
            Text("设置")
        },
        onClick = {
            router.navigate("setting")
        }
    )
}

@Composable
private fun DrawerItem(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    tail: @Composable () -> Unit = {},
    onClick: () -> Unit
) {
    ProvideTextStyle(MaterialTheme.typography.titleMedium) {
        Surface(
            //tonalElevation = 4.dp,
            // shadowElevation = 4.dp,
            shape = RoundedCornerShape(50),
            onClick = {
                onClick()
            },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                icon()
                label()
                Spacer(modifier = Modifier.weight(1f))
                tail()
            }
        }
    }
}