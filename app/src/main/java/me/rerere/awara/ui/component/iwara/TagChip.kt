package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.awara.data.entity.Tag

@Composable
fun TagChip(tag: Tag) {
    AssistChip(
        onClick = { /*TODO*/ },
        label = {
            Text(
                text = tag.id,
            )
        }
    )
}

@Composable
fun TagRow(
    modifier: Modifier = Modifier,
    tags: List<Tag>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(tags) {
            TagChip(tag = it)
        }
    }
}