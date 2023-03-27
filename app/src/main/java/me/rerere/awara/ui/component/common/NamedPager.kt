package me.rerere.awara.ui.component.common

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun NamedHorizontalPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    pages: List<String>,
    content: @Composable (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    HorizontalPager(
        modifier = modifier,
        pageCount = pages.size,
        state = state
    ) { page ->
        content(pages[page])
    }
}