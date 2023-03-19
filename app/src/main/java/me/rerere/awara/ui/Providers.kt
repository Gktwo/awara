package me.rerere.awara.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import me.rerere.awara.ui.component.common.MessageHolder

val LocalRouterProvider = staticCompositionLocalOf<NavController> {
    error("Not init yet")
}

val LocalMessageProvider = staticCompositionLocalOf<MessageHolder> {
    error("Not init yet")
}