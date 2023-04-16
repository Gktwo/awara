package me.rerere.awara.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun ImageTopBar(
    state: ImageTopBarState,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
    image: @Composable (() -> Unit)? = null,
) {
    val density = LocalDensity.current
    val height by remember {
        derivedStateOf {
            with(density) {
                state.offset.times(-1).toDp().coerceIn(0.dp..200.dp)
            }
        }
    }
    Surface {
        if(height == 200.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navigationIcon?.invoke()

                title?.invoke()

                Spacer(modifier = Modifier.weight(1f))

                actions?.invoke()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp - height)
            ) {
                image?.invoke()

                CompositionLocalProvider(LocalContentColor provides Color.White) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        navigationIcon?.invoke()

                        title?.invoke()

                        Spacer(modifier = Modifier.weight(1f))

                        actions?.invoke()
                    }
                }
            }
        }
    }
}

@Stable
class ImageTopBarState {
    var offset by mutableStateOf(0f)

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (consumed.y == 0f && available.y > 0f) {
                offset = 0f
            } else {
                offset += consumed.y
            }
            return super.onPostScroll(consumed, available, source)
        }
    }
}

@Composable
fun rememberImageTopBarState(): ImageTopBarState {
    return remember { ImageTopBarState() }
}