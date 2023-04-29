package me.rerere.awara.ui.page.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.rerere.awara.R
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.BetterTabBar
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.ext.DynamicStaggeredGridCells
import me.rerere.awara.ui.component.ext.excludeBottom
import me.rerere.awara.ui.component.ext.onlyBottom
import me.rerere.awara.ui.component.iwara.MediaCard
import me.rerere.awara.ui.component.iwara.PaginationBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesPage(vm: FavoritesVM = koinViewModel()) {
    val appbarBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(stringResource(R.string.favorites))
                },
                navigationIcon = {
                    BackButton()
                },
                scrollBehavior = appbarBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding.excludeBottom())
        ) {
            BetterTabBar(
                selectedTabIndex = pagerState.currentPage
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                    text = {
                        Text(stringResource(R.string.video))
                    }
                )

                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                    text = {
                        Text(stringResource(R.string.image))
                    }
                )
            }

            HorizontalPager(
                pageCount = 2,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                when (page) {
                    0 -> {
                        Column {
                            Spin(
                                show = vm.state.videoLoading,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                LazyVerticalStaggeredGrid(
                                    columns = DynamicStaggeredGridCells(),
                                    contentPadding = PaddingValues(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalItemSpacing = 8.dp,
                                    modifier = Modifier
                                        .matchParentSize()
                                        .nestedScroll(appbarBehavior.nestedScrollConnection)
                                ) {
                                    items(vm.state.videoList) { video ->
                                        MediaCard(media = video.video)
                                    }
                                }
                            }

                            PaginationBar(
                                page = vm.state.videoPage,
                                limit = 32,
                                total = vm.state.videoCount,
                                onPageChange = {
                                    vm.jumpToVideoPage(it)
                                },
                                contentPadding = innerPadding.onlyBottom()
                            )
                        }
                    }

                    1 -> {
                        Column {
                            Spin(
                                show = vm.state.imageLoading,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                LazyVerticalStaggeredGrid(
                                    columns = DynamicStaggeredGridCells(),
                                    contentPadding = PaddingValues(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalItemSpacing = 8.dp,
                                    modifier = Modifier
                                        .matchParentSize()
                                        .nestedScroll(appbarBehavior.nestedScrollConnection)
                                ) {
                                    items(vm.state.imageList) { image ->
                                        MediaCard(media = image.image)
                                    }
                                }
                            }

                            PaginationBar(
                                page = vm.state.imagePage,
                                limit = 32,
                                total = vm.state.imageCount,
                                onPageChange = {
                                    vm.jumpToImagePage(it)
                                },
                                contentPadding = innerPadding.onlyBottom()
                            )
                        }
                    }
                }
            }
        }
    }
}