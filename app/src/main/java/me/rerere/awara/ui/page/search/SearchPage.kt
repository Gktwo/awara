package me.rerere.awara.ui.page.search

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.iwara.PaginationBar

@Composable
fun SearchPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.search))
                },
                navigationIcon = {
                    BackButton()
                }
            )
        },
        bottomBar = {
            PaginationBar(
                page = 1,
                limit = 32,
                total = 0,
                onPageChange = {},
                contentPadding = WindowInsets.navigationBars.asPaddingValues()
            )
        }
    ) {
        Spin(
            show = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

        }
    }
}
