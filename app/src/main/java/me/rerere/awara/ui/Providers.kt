package me.rerere.awara.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalRouterProvider = staticCompositionLocalOf<NavController> {
    error("Not init yet")
}