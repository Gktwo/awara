package me.rerere.awara.ui.page.user

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import me.rerere.awara.data.dto.ProfileDto
import me.rerere.awara.data.entity.toHeaderUrl
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.BetterTabBar
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.common.ButtonType
import me.rerere.awara.ui.component.common.EmptyStatus
import me.rerere.awara.ui.component.common.ImageAppBar
import me.rerere.awara.ui.component.common.TodoStatus
import me.rerere.awara.ui.component.common.pullrefresh.SwipeRefresh
import me.rerere.awara.ui.component.common.pullrefresh.rememberSwipeRefreshState
import me.rerere.awara.ui.component.ext.DynamicStaggeredGridCells
import me.rerere.awara.ui.component.ext.excludeBottom
import me.rerere.awara.ui.component.iwara.Avatar
import me.rerere.awara.ui.component.iwara.MediaCard
import me.rerere.awara.ui.component.iwara.PaginationBar
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.util.openUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserPage(
    vm: UserVM = koinViewModel()
) {
    val profileState = vm.state.profile
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var expand by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (expand) {
                ImageAppBar(
                    title = {
                        Text(
                            text = profileState?.user?.name ?: "",
                        )
                    },
                    navigationIcon = {
                        BackButton()
                    },
                    image = {
                        AsyncImage(
                            model = profileState?.header.toHeaderUrl(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    },
                    //scrollBehavior = appBarState,
                )
            } else {
                TopAppBar(
                    title = {
                        Text(
                            text = profileState?.user?.name ?: "",
                        )
                    },
                    navigationIcon = {
                        BackButton()
                    },
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it.excludeBottom()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            UserCard(
                vm = vm,
                profileState = profileState,
                expand = expand,
                onChangeExpand = { expand = it },
                onFollow = {
                    vm.followOrUnfollow()
                }
            )

            Column {
                val pagerState = rememberPagerState()
                BetterTabBar(selectedTabIndex = pagerState.currentPage) {
                    Tab(
                        selected = pagerState.currentPage == 0,
                        onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                        text = {
                            Text("视频")
                        }
                    )
                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                        text = {
                            Text("图片")
                        }
                    )
                    Tab(
                        selected = pagerState.currentPage == 2,
                        onClick = { scope.launch { pagerState.animateScrollToPage(2) } },
                        text = {
                            Text("留言")
                        }
                    )
                }

                HorizontalPager(
                    state = pagerState,
                    pageCount = 4,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> {
                            VideoPage(vm)
                        }

                        1 -> {
                            ImagePage(vm)
                        }

                        2 -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                TodoStatus()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserCard(
    vm: UserVM,
    profileState: ProfileDto?,
    expand: Boolean,
    onChangeExpand: (Boolean) -> Unit,
    onFollow: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Avatar(
                user = profileState?.user,
                modifier = Modifier
                    .size(48.dp)
            )

            Column {
                Text(
                    text = profileState?.user?.name ?: "",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "@" + (profileState?.user?.username ?: ""),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                },
                type = if (profileState?.user?.friend == true) ButtonType.Outlined else ButtonType.Default,
            ) {
                Text(
                    text = if (profileState?.user?.friend == true) "已是好友" else "好友",
                )
            }

            Button(
                onClick = {
                    onFollow()
                },
                type = if (profileState?.user?.following == true) ButtonType.Outlined else ButtonType.Default,
                loading = vm.state.followLoading
            ) {
                Text(
                    text = if (profileState?.user?.following == true) "已关注" else "关注",
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RichText(
                text = profileState?.body ?: "",
                onLinkClick = { url ->
                    context.openUrl(url)
                },
                maxLines = if (expand) Int.MAX_VALUE else 1,
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize(),
                overflow = TextOverflow.Ellipsis,
            )

            IconButton(onClick = { onChangeExpand(!expand) }) {
                Icon(
                    if (expand) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    null
                )
            }
        }
    }
}

@Composable
private fun VideoPage(vm: UserVM) {
    Column {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = vm.state.videoLoading),
            onRefresh = {
                vm.loadVideoList()
            },
            modifier = Modifier.weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (vm.state.videoList.isEmpty()) {
                    EmptyStatus()
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = DynamicStaggeredGridCells(150.dp, 2, 4),
                        modifier = Modifier.fillMaxSize(),
                        verticalItemSpacing = 8.dp,
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(vm.state.videoList) {
                            MediaCard(media = it)
                        }
                    }
                }
            }
        }
        PaginationBar(
            page = vm.state.videoPage,
            limit = 32,
            total = vm.state.videoCount,
            onPageChange = {
                vm.changeVideoPage(it)
            },
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        )
    }
}

@Composable
private fun ImagePage(vm: UserVM) {
    Column {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = vm.state.imageLoading),
            onRefresh = {
                vm.loadImageList()
            },
            modifier = Modifier.weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (vm.state.imageList.isEmpty()) {
                    EmptyStatus()
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = DynamicStaggeredGridCells(150.dp, 2, 4),
                        modifier = Modifier.fillMaxSize(),
                        verticalItemSpacing = 8.dp,
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(vm.state.imageList) {
                            MediaCard(media = it)
                        }
                    }
                }
            }
        }
        PaginationBar(
            page = vm.state.imagePage,
            limit = 32,
            total = vm.state.imageCount,
            onPageChange = {
                vm.changeImagePage(it)
            },
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        )
    }
}
