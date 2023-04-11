package me.rerere.awara.ui.component.iwara

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.awara.data.entity.Comment

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    comment: Comment,
    onLoadReplies: (Comment) -> List<Comment>,
    onReply: (Comment) -> Unit
) {
    var replies by remember { mutableStateOf(listOf<Comment>()) }
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Avatar(user = comment.user)
                Text(
                    text = comment.user.name,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Text(
                text = comment.body,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        replies = onLoadReplies(comment)
                    }
                ) {
                    Text("共${comment.numReplies}条回复")
                }

                TextButton(
                    onClick = {
                        onReply(comment)
                    }
                ) {
                    Text("回复")
                }
            }

            replies.forEach {
                CommentCard(
                    modifier = modifier,
                    comment = it,
                    onLoadReplies = onLoadReplies,
                    onReply = onReply
                )
            }
        }
    }
}