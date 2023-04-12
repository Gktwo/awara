package me.rerere.awara.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BetterTabBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    content: @Composable () -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        indicator = {
            Box(
                modifier
                    .wrapContentSize(Alignment.BottomStart)
                    .offset(
                        x = (it[selectedTabIndex].left + it[selectedTabIndex].right) / 2 - 16.dp,
                    )
                    .width(32.dp)
                    .height(3.dp)
                    .background(color = MaterialTheme.colorScheme.primary)
            )
        },
        edgePadding = 4.dp,
        divider = {},
        containerColor = Color.Transparent
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun TabBarPreview() {
    BetterTabBar(selectedTabIndex = 0) {
        Tab(selected = false, onClick = { /*TODO*/ }) {
            Text(text = "Tab 1")
        }

        Tab(selected = true, onClick = { /*TODO*/ }) {
            Text(text = "Tab 2")
        }

        Tab(selected = false, onClick = { /*TODO*/ }) {
            Text(text = "Tab 3")
        }
    }
}

