package me.rerere.awara.ui.component.iwara.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterAndSort(
    modifier: Modifier = Modifier,
    sort: String?,
    onSortChange: (String) -> Unit,
    sortOptions: List<SortOption>,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (sortOptions.isNotEmpty()) {
            SortButton(
                modifier = modifier,
                sort = sort,
                onSortChange = onSortChange,
                sortOptions = sortOptions
            )
        }

//        Badge(
//            modifier = Modifier.align(Alignment.TopEnd),
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.onPrimary,
//        ) {
//            Text(filterValues.size.toString())
//        }
    }
}