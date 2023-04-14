package me.rerere.awara.ui.page.image

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import me.rerere.awara.data.entity.toHeaderUrl
import me.rerere.awara.data.entity.toImageLarge
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Spin
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImagePage(vm: ImageVM = koinViewModel()) {
    val state = vm.state
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Image")
                },
                navigationIcon = {
                    BackButton()
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Spacer(modifier = Modifier.weight(1f))

                FilledTonalButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.ModeComment, null)
                }

                FilledTonalButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.FavoriteBorder, null)
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
                HorizontalPager(pageCount = state.state?.files?.size ?: 0) {
                    AsyncImage(
                        model = state.state!!.files[it].toImageLarge(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .zoomable(rememberZoomState())
                    )
                }
            }
        }
    }
}