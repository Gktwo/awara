package me.rerere.awara.ui.component.iwara.comment

import me.rerere.awara.data.entity.Comment

data class CommentState(
    val loading: Boolean = false,
    val stack: List<CommentStateItem> = listOf(CommentStateItem())
)

data class CommentStateItem(
    val page: Int = 1,
    val limit: Int = 32,
    val total: Int = 0,
    val parent: String? = null,
    val comments: List<Comment> = emptyList(),
)

fun CommentState.push(id: String): CommentState {
    return copy(
        stack = stack + CommentStateItem(parent = id)
    )
}

fun CommentState.pop(): CommentState {
    return copy(
        stack = stack.dropLast(1)
    )
}

fun CommentState.updatePage(page: Int): CommentState {
    return copy(
        stack = stack.dropLast(1) + stack.last().copy(page = page)
    )
}

fun CommentState.updateTopStack(item: CommentStateItem): CommentState {
    return copy(
        stack = stack.dropLast(1) + item
    )
}