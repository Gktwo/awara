package me.rerere.awara.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Avatar(
    model: String,
    onClick: () -> Unit = {}
) {
    AsyncImage(
        model = model,
        contentDescription = "avatar",
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .size(32.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
    )
}