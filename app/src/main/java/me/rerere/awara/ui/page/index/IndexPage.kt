package me.rerere.awara.ui.page.index

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLabel
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.rerere.awara.R
import me.rerere.awara.ui.component.common.Avatar
import me.rerere.awara.ui.hooks.rememberWindowSize
import org.koin.androidx.compose.koinViewModel

@Composable
fun IndexPage(
    vm: IndexVM = koinViewModel()
) {
    val windowSizeClass = rememberWindowSize()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            IndexPageTabletLayout(vm)
        }

        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Compact -> {
            IndexPagePhoneLayout(vm)
        }
    }
}

@Composable
private fun IndexPageTabletLayout(vm: IndexVM) {
    val pagerState = rememberPagerState()
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    Row {
        NavigationRail {
            navigations.forEachIndexed { index, navigationPoint ->
                NavigationRailItem(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        // TODO: Handle navigation
                    },
                    icon = navigationPoint.icon,
                    label = navigationPoint.title
                )
            }
        }
        Scaffold(
            topBar = {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                    },
                    active = active,
                    onActiveChange = { active = it },
                    leadingIcon = {
                        Avatar(
                            model = "https://iwara.tv/images/default-avatar.jpg",
                        )
                    },
                    placeholder = {
                        Text("今天你想搜点什么?")
                    },
                    trailingIcon = {
                        if (active) {
                            IconButton(
                                onClick = { active = false }
                            ) {
                                Icon(Icons.Outlined.Close, "Close")
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    active = true
                                }
                            ) {
                                Icon(Icons.Outlined.Search, "Search")
                            }
                        }
                    }
                ) {}
            }
        ) { padding ->
            HorizontalPager(
                pageCount = 3,
                state = pagerState,
                modifier = Modifier.padding(padding)
            ) {

            }
        }
    }
}

@Composable
private fun IndexPagePhoneLayout(vm: IndexVM) {
    val pagerState = rememberPagerState()
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                query = query,
                onQueryChange = {
                    query = it
                },
                onSearch = {
                },
                active = active,
                onActiveChange = { active = it },
                leadingIcon = {
                    Avatar(
                        model = "https://iwara.tv/images/default-avatar.jpg",
                    )
                },
                placeholder = {
                    Text("今天你想搜点什么?")
                },
                trailingIcon = {
                    if (active) {
                        IconButton(
                            onClick = { active = false }
                        ) {
                            Icon(Icons.Outlined.Close, "Close")
                        }
                    } else {
                        IconButton(
                            onClick = {
                                active = true
                            }
                        ) {
                            Icon(Icons.Outlined.Search, "Search")
                        }
                    }
                }
            ) {

            }
        },
        bottomBar = {
            NavigationBar {
                navigations.forEachIndexed { index, navigationPoint ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // TODO: Handle navigation
                        },
                        icon = navigationPoint.icon,
                        label = navigationPoint.title
                    )
                }
            }
        }
    ) {
        HorizontalPager(
            pageCount = 3,
            state = pagerState,
            modifier = Modifier.padding(it)
        ) {

        }
    }
}


private data class NavigationPoint(
    val title: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
)

private val navigations = listOf(
    NavigationPoint(
        title = {
            Text(stringResource(R.string.index_nav_video))
        },
        icon = {
            Icon(Icons.Outlined.VideoLabel, "Videos")
        },
    ),
    NavigationPoint(
        title = {
            Text(stringResource(R.string.index_nav_image))
        },
        icon = {
            Icon(Icons.Outlined.Image, "Images")
        },
    ),
    NavigationPoint(
        title = {
            Text(stringResource(R.string.index_nav_video))
        },
        icon = {
            Icon(Icons.Outlined.Forum, "Forum")
        },
    ),
)