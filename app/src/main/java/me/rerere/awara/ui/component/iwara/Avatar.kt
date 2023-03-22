package me.rerere.awara.ui.component.iwara

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.awara.data.entity.User
import me.rerere.awara.data.entity.toAvatarUrl

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    user: User?,
    onClick: () -> Unit = {}
) {
    me.rerere.awara.ui.component.common.Avatar(
        model = (user?.avatar).toAvatarUrl(),
        modifier = modifier,
        onClick = onClick
    )
}