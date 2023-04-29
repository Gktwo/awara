package me.rerere.awara.ui.page.image

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.rerere.awara.R
import me.rerere.awara.data.entity.toImageLarge
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.common.ButtonType
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.common.zoomable.rememberZoomState
import me.rerere.awara.ui.component.common.zoomable.zoomable
import me.rerere.awara.ui.component.iwara.AuthorCard
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.util.openUrl
import me.rerere.awara.util.shareLink
import me.rerere.awara.util.toLocalDateTimeString
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImagePage(vm: ImageVM = koinViewModel()) {
    val state = vm.state
    val context = LocalContext.current
    val router = LocalRouterProvider.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.state?.title ?: "Image",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    BackButton()
                }
            )
        },
        bottomBar = {
            Surface {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .navigationBarsPadding()
                        .animateContentSize()
                        .padding(16.dp),
                ) {
                    Card {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            var expaned by remember { mutableStateOf(false) }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    text = state.state?.title ?: "",
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = if (expaned) Int.MAX_VALUE else 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    if (expaned) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clickable {
                                            expaned = !expaned
                                        }
                                        .padding(4.dp)
                                )
                            }

                            if (expaned) {
                                RichText(
                                    text = state.state?.body ?: "",
                                    overflow = TextOverflow.Ellipsis,
                                    onLinkClick = {
                                        context.openUrl(it)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    maxLines = if (expaned) Int.MAX_VALUE else 2,
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    text = "Views: ${state.state?.numViews?.toString() ?: ""}",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Text(
                                    text = "Likes ${state.state?.numLikes?.toString() ?: ""}",
                                    style = MaterialTheme.typography.labelMedium
                                )

                                Text(
                                    text = state.state?.createdAt?.toLocalDateTimeString() ?: "",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = {
                                        context.shareLink("https://www.iwara.tv/image/${vm.id}")
                                    }
                                ) {
                                    Icon(Icons.Outlined.Share, null)
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(
                                    onClick = { vm.likeOrDislike() },
                                    type = if (state.state?.liked == true) ButtonType.Outlined else ButtonType.Default,
                                    loading = state.likeLoading
                                ) {
                                    Text(
                                        text = if (state.state?.liked == true) stringResource(R.string.dislike) else stringResource(
                                            R.string.like
                                        )
                                    )
                                }
                            }
                        }
                    }

                    AuthorCard(user = state.state?.user, onClickSub = { vm.followOrUnfollow() })
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Spin(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize(),
                show = state.loading
            ) {
                val pagerState = rememberPagerState()
                HorizontalPager(
                    pageCount = state.state?.files?.size ?: 0,
                    state = pagerState
                ) {
                    AsyncImage(
                        model = state.state!!.files[it].toImageLarge(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .zoomable(rememberZoomState())
                    )
                }

                Text(
                    text = "${pagerState.currentPage + 1}/${state.state?.files?.size}",
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    color = Color.White
                )
            }
        }
    }
}