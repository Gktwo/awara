package me.rerere.awara.ui.page.video.pager

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.awara.ui.component.ext.excludeBottom
import me.rerere.awara.ui.component.ext.onlyBottom
import me.rerere.awara.ui.component.iwara.comment.CommentList
import me.rerere.awara.ui.page.video.VideoVM

@Composable
fun VideoCommentPage(vm: VideoVM) {
    CommentList(
        modifier = Modifier.consumeWindowInsets(WindowInsets.navigationBars),
        state = vm.state.commentState,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        onPageChange = vm::jumpCommentPage,
        onPush = {
            vm.pushComment(it)
        },
        onBack = {
            vm.popComment()
        },
        onPostReply = {
            vm.postComment(it)
        }
    )
}
