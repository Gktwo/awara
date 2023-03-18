package me.rerere.awara.ui.component.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import me.rerere.awara.ui.LocalRouterProvider

@Composable
fun BackButton() {
    val router = LocalRouterProvider.current
    IconButton(
        onClick = {
            router.popBackStack()
        }
    ) {
        Icon(Icons.Outlined.ArrowBack, "Back")
    }
}