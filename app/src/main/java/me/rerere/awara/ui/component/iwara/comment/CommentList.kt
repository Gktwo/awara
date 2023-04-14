package me.rerere.awara.ui.component.iwara.comment

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import me.rerere.awara.data.entity.Comment
import me.rerere.awara.data.entity.CommentCreationDto
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.iwara.PaginationBar

@Composable
fun CommentList(
    modifier: Modifier = Modifier,
    state: CommentState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onPageChange: (Int) -> Unit,
    onBack: () -> Unit,
    onPush: (String) -> Unit,
    onPostReply: (CommentCreationDto) -> Unit,
) {
    val currentComment = state.stack.last()
    var repling by remember {
        mutableStateOf(false)
    }
    var replyTo by remember {
        mutableStateOf<Comment?>(null)
    }

    BackHandler(state.stack.size > 1) {
        onBack()
        replyTo = null
    }

    Column(
        modifier = modifier,
    ) {
        AnimatedVisibility(visible = state.stack.size > 1) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            onBack()
                            replyTo = null
                        }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }

                    Text("评论详情")
                }
            }
        }

        Spin(
            show = state.loading,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(currentComment.comments) { comment ->
                    CommentCard(
                        comment = comment,
                        onLoadReplies = {
                            onPush(it.id)
                            replyTo = it
                        },
                        onReply = {
                            repling = true
                            replyTo = it
                        }
                    )
                }
            }
        }

        AnimatedContent(
            targetState = repling,
            label = "ReplyBar",
            transitionSpec = {
                if (targetState) {
                    slideInHorizontally(
                        initialOffsetX = { -it }
                    ) with slideOutHorizontally(
                        targetOffsetX = { -it }
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { it }
                    ) with slideOutHorizontally(
                        targetOffsetX = { it }
                    )
                }
            }
        ) {
            if (it) {
                Surface(
                    modifier = Modifier
                        .imePadding()
                        .fillMaxWidth(),
                    tonalElevation = 4.dp,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(contentPadding)
                            .padding(6.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(onClick = { repling = false }) {
                            Icon(Icons.Outlined.Close, null)
                        }

                        var body by remember {
                            mutableStateOf("")
                        }
                        TextField(
                            value = body,
                            onValueChange = { body = it },
                            modifier = Modifier
                                .weight(1f),
                            placeholder = {
                                Text(
                                    text = if (replyTo == null) {
                                        "回复"
                                    } else {
                                        "回复 ${replyTo?.user?.name}"
                                    }
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = false,
                                imeAction = ImeAction.Send,
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                    onPostReply(CommentCreationDto(
                                        body = body,
                                        parentId = replyTo?.id,
                                    ))
                                }
                            )
                        )

                        FilledTonalButton(
                            onClick = {
                                onPostReply(CommentCreationDto(
                                    body = body,
                                    parentId = replyTo?.id,
                                ))
                            }
                        ) {
                            Icon(Icons.Outlined.Send, null)
                        }
                    }
                }
            } else {
                PaginationBar(
                    page = currentComment.page,
                    limit = currentComment.limit,
                    total = currentComment.total,
                    onPageChange = onPageChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = contentPadding,
                    trailing = {
                        FilledTonalButton(
                            onClick = {
                                repling = true
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ModeComment,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        }
    }
}