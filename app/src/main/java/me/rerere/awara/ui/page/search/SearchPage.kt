package me.rerere.awara.ui.page.search

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.BackButton

@Composable
fun SearchPage() {
    val router = LocalRouterProvider.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        Text("Search TODO")
                },
                navigationIcon = {
                    BackButton()
                }
            )
        }
    ) {

    }
}