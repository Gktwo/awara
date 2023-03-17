package me.rerere.awara.ui.page.index

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import me.rerere.awara.ui.hooks.rememberWindowSize
import org.koin.androidx.compose.koinViewModel

@Composable
fun IndexPage(
    vm: IndexVM = koinViewModel()
) {
    val windowSizeClass = rememberWindowSize()
    Row {
        if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
            NavigationRail(
                header = {
                    Text("Header")
                }
            ) {
                NavigationRailItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Outlined.Home, contentDescription = "Home")
                        }
                    }
                )

                NavigationRailItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Outlined.Home, contentDescription = "Home")
                        }
                    }
                )

                NavigationRailItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Outlined.Home, contentDescription = "Home")
                        }
                    }
                )
            }
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Index")
                    }
                )
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                Button(
                    onClick = {

                    }
                ) {
                    Text("Hello World!")
                }

                Text(
                    windowSizeClass.toString()
                )
            }
        }
    }
}