package me.rerere.awara.ui.page.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.rerere.awara.data.entity.toHeaderUrl
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.ImageTopBar
import me.rerere.awara.ui.component.common.rememberImageTopBarState
import me.rerere.awara.ui.component.iwara.Avatar
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
                    Text(profileState?.user?.name ?: "")
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
            item {
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Avatar(
                            user = profileState?.user,
                            modifier = Modifier.size(32.dp)
                        )

                        Column {
                            Text(
                                text = profileState?.user?.name ?: "",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = profileState?.user?.username ?: "",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(onClick = { /*TODO*/ }) {
                            Text("关注")
                        }

                        Button(onClick = { /*TODO*/ }) {
                            Text("朋友")
                        }

                        Button(onClick = { /*TODO*/ }) {
                            Text("消息")
                        }
                    }
                }
            }

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