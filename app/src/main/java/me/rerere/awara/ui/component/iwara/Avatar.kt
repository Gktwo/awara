package me.rerere.awara.ui.component.iwara

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.awara.data.entity.User

private const val DEFAULT_AVATAR = "https://iwara.tv/images/default-avatar.jpg"

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    user: User?,
    onClick: () -> Unit = {}
) {
    me.rerere.awara.ui.component.common.Avatar(
        model = if(user?.avatar == null) {
            DEFAULT_AVATAR
        } else {
            "https://files.iwara.tv/image/avatar/${user.avatar.id}/${user.avatar.name}"
        },
        modifier = modifier
    )
}