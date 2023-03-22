package me.rerere.awara.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import me.rerere.awara.ui.component.common.DialogHolder
import me.rerere.awara.ui.component.common.MessageHolder

/**
 * Local router holder.
 * You can use this to navigate between screens
 *
 * Example:
 * ```
 * LocalRouterProvider.current.navigate("home")
 * ```
 */
val LocalRouterProvider = staticCompositionLocalOf<NavController> {
    error("Not init yet")
}

/**
 * Local message holder.
 * You can use this to show message
 *
 * Example:
 * ```
 * LocalMessageProvider.current.info("Hello World")
 * ```
 */
val LocalMessageProvider = staticCompositionLocalOf<MessageHolder> {
    error("Not init yet")
}


/**
 * Local dialog holder.
 */
val LocalDialogProvider = staticCompositionLocalOf<DialogHolder> {
    error("DialogProvider not found")
}
