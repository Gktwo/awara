package me.rerere.awara.ui.page.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.rerere.awara.data.entity.toHeaderUrl
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.ImageTopBar
import me.rerere.awara.ui.component.common.rememberImageTopBarState
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserPage(
    vm: UserVM = koinViewModel()
) {
    val profileState = vm.state.profile
    val topBarState = rememberImageTopBarState()
    Scaffold(
        topBar = {
            ImageTopBar(
                state = topBarState,
                image = {
                    AsyncImage(
                        model = profileState?.header.toHeaderUrl(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        contentScale = ContentScale.Crop
                    )
                },
                navigationIcon = {
                    BackButton()
                },
                title = {
                    Text("测试")
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .nestedScroll(topBarState.nestedScrollConnection),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(20) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(vm.state.toString())
                }
            }
        }
    }
}