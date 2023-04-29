package me.rerere.awara.ui.page.index.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.rerere.awara.R
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.NamedHorizontalPager
import me.rerere.awara.ui.component.common.TodoStatus
import me.rerere.awara.ui.component.iwara.Avatar
import me.rerere.awara.ui.page.index.IndexDrawer
import me.rerere.awara.ui.page.index.IndexVM
import me.rerere.awara.ui.page.index.indexNavigations
import me.rerere.awara.ui.page.index.pager.IndexImagePage
import me.rerere.awara.ui.page.index.pager.IndexSubscriptionPage
import me.rerere.awara.ui.page.index.pager.IndexVideoPage
import me.rerere.awara.ui.stores.LocalUserStore
import me.rerere.awara.ui.stores.collectAsState

@Composable
fun IndexPageTabletLayout(vm: IndexVM) {
    val userStore = LocalUserStore.current
    val userState = userStore.collectAsState()
    val navController = LocalRouterProvider.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navigations = remember(userState.user) {
        indexNavigations.filter {
            !it.needLogin || userState.user != null
        }
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                IndexDrawer(vm)
            }
        },
        drawerState = drawerState,
    ) {
        Row {
            NavigationRail {
                navigations.forEachIndexed { index, indexNavigation ->
                    NavigationRailItem(
                        icon = {
                            indexNavigation.icon()
                        },
                        label = {
                            indexNavigation.title()
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        }
                    )
                }
            }
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = userState.user?.name ?: stringResource(R.string.app_name)
                            )
                        },
                        navigationIcon = {
                            Avatar(
                                user = userState.user,
                                onClick = {
                                    scope.launch { drawerState.open() }
                                },
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    navController.navigate("search")
                                }
                            ) {
                                Icon(Icons.Outlined.Search, "Search")
                            }
                        }
                    )
                },
            ) { innerPadding ->
                NamedHorizontalPager(
                    state = pagerState,
                    modifier = Modifier.padding(innerPadding),
                    pages = navigations.map { it.name },
                ) { page ->
                    when (page) {
                        "subscription" -> {
                            IndexSubscriptionPage(vm)
                        }

                        "video" -> {
                            IndexVideoPage(vm)
                        }

                        "image" -> {
                            IndexImagePage(vm)
                        }

                        "forum" -> {
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