package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.rerere.awara.R

@Composable
fun PaginationBar(
    modifier: Modifier = Modifier,
    page: Int,
    onPageChange: (Int) -> Unit,
) {
    Surface(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Previous page
            IconButton(
                onClick = {
                    onPageChange(page - 1)
                }
            ) {
                Icon(Icons.Outlined.KeyboardArrowLeft, "Previous page")
            }

            // Current page
            Text(
                text = stringResource(R.string.pagination_current, page),
                modifier = Modifier.clickable {

                }
            )

            // Next page
            IconButton(
                onClick = {
                    onPageChange(page + 1)
                }
            ) {
                Icon(Icons.Outlined.KeyboardArrowRight, "Next page")
            }
        }
    }
}

@Preview
@Composable
private fun PaginationPreview() {
    PaginationBar(page = 1, onPageChange = {})
}