package me.rerere.awara.ui.component.iwara.comment

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.awara.data.repo.CommentRepo
import org.koin.androidx.compose.get

@Composable
fun CommentList(
    modifier: Modifier = Modifier,
    resourcePath: String,
    commentRepo: CommentRepo = get()
) {
    LazyColumn(
        modifier = modifier
    ) {
        // TODO: Add comments
    }
}