package me.rerere.awara.ui.page.video.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.component.ext.plus
import me.rerere.awara.ui.component.iwara.CommentCard
import me.rerere.awara.ui.component.iwara.PaginationBar
import me.rerere.awara.ui.page.video.VideoVM

@Composable
fun VideoCommentPage(vm: VideoVM) {
    val state = vm.state
    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.comments) {
                CommentCard(
                    comment = it,
                    onLoadReplies = {
                        emptyList()
                    },
                    onReply = {}
                )
            }
        }

        BottomAppBar(
            modifier = Modifier.imePadding()
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text("请友善评论")
                },
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            FilledTonalIconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.Send, null)
            }
        }
    }
}
