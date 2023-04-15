package me.rerere.awara.ui.page.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.rerere.awara.data.entity.toImageLarge
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.common.zoomable.rememberZoomState
import me.rerere.awara.ui.component.common.zoomable.zoomable
import me.rerere.awara.ui.component.iwara.Avatar
import me.rerere.awara.ui.component.iwara.RichText
import me.rerere.awara.util.openUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImagePage(vm: ImageVM = koinViewModel()) {
    val state = vm.state
    val context = LocalContext.current
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
                        .padding(8.dp)
                ) {
                    Text(
                        text = state.state?.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    RichText(
                        text = state.state?.body ?: "",
                        overflow = TextOverflow.Ellipsis,
                        onLinkClick = {
                            context.openUrl(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 3
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Avatar(
                            user = state.state?.user,
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f)
                        )

                        Text(
                            text = state.state?.user?.name ?: "",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Views: ${state.state?.numViews?.toString() ?: ""}",
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = "Likes ${state.state?.numLikes?.toString() ?: ""}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
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
                HorizontalPager(pageCount = state.state?.files?.size ?: 0, state = pagerState) {
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