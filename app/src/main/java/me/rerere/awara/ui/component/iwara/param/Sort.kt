package me.rerere.awara.ui.component.iwara.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.util.fastForEach

@Composable
fun SortButton(
    modifier: Modifier = Modifier,
    sort: String?,
    onSortChange: (String) -> Unit,
    sortOptions: List<SortOption>,
) {
    var showPopup by remember { mutableStateOf(false) }
    val currentSort = sortOptions.fastFirstOrNull { it.name == sort } ?: sortOptions.first()
    FilledTonalButton(
        onClick = {
            showPopup = !showPopup
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (currentSort.icon != null) {
                currentSort.icon.invoke()
            } else {
                Icon(Icons.Outlined.Sort, "Sort")
            }
            currentSort.label.invoke()
        }
    }
    if (showPopup) {
        DropdownMenu(
            expanded = showPopup,
            onDismissRequest = { showPopup = false }
        ) {
            sortOptions.fastForEach { option ->
                DropdownMenuItem(
                    text = {
                        option.label.invoke()
                    },
                    onClick = {
                        onSortChange(option.name)
                        showPopup = false
                    },
                    leadingIcon = if (option.icon != null) {
                        {
                            option.icon.invoke()
                        }
                    } else null
                )
            }
        }
    }
}

class SortOption(
    val name: String,
    val label: @Composable () -> Unit,
    val icon: (@Composable () -> Unit)? = null,
)