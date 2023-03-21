package me.rerere.awara.ui.store

import androidx.compose.runtime.staticCompositionLocalOf
import me.rerere.awara.data.entity.User

val LocalUserState = staticCompositionLocalOf<UserState> {
    error("No UserState provided")
}

data class UserState(
    val token: String? = null,
    val user: User? = null,
)