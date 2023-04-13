package me.rerere.awara.ui.component.iwara.comment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.awara.data.entity.Comment
import me.rerere.awara.ui.component.iwara.Avatar

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    comment: Comment,
    onLoadReplies: (Comment) -> Unit,
    onReply: (Comment) -> Unit
) {
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Avatar(user = comment.user)
                Text(
                    text = comment.user?.name ?: "",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                text = comment.body,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedVisibility(
                    visible = comment.numReplies > 0
                ) {
                    TextButton(
                        onClick = {
                            onLoadReplies(comment)
                        }
                    ) {
                        Text("共${comment.numReplies}条回复")
                    }
                }

                TextButton(
                    onClick = {
                        onReply(comment)
                    }
                ) {
                    Text("回复")
                }
            }
        }
    }
}