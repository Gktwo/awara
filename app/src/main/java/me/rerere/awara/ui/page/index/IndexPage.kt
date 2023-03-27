package me.rerere.awara.ui.page.index

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material.icons.outlined.VideoLabel
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R
import me.rerere.awara.ui.hooks.rememberWindowSize
import me.rerere.awara.ui.page.index.layout.IndexPagePhoneLayout
import me.rerere.awara.ui.page.index.layout.IndexPageTabletLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun IndexPage(
    vm: IndexVM = koinViewModel()
) {
    val windowSizeClass = rememberWindowSize()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded, WindowWidthSizeClass.Medium -> {
            IndexPageTabletLayout(vm)
        }

        WindowWidthSizeClass.Compact -> {
            IndexPagePhoneLayout(vm)
        }
    }
}

data class IndexNavigation(
    val name: String,
    val title: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
    val needLogin: Boolean = false,
)

val indexNavigations = listOf(
    IndexNavigation(
        name = "subscription",
        title = {
            Text(stringResource(R.string.index_nav_subscription))
        },
        icon = {
            Icon(Icons.Outlined.Subscriptions, "Subscription")
        },
        needLogin = true
    ),
    IndexNavigation(
        name = "video",
        title = {
            Text(stringResource(R.string.index_nav_video))
        },
        icon = {
            Icon(Icons.Outlined.VideoLabel, "Videos")
        },
    ),
    IndexNavigation(
        name = "image",
        title = {
            Text(stringResource(R.string.index_nav_image))
        },
        icon = {
            Icon(Icons.Outlined.Image, "Images")
        },
    ),
    IndexNavigation(
        name = "forum",
        title = {
            Text(stringResource(R.string.index_nav_forum))
        },
        icon = {
            Icon(Icons.Outlined.Forum, "Forum")
        },
    ),
)