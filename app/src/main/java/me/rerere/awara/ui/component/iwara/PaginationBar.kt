package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.rerere.awara.R
import me.rerere.awara.ui.LocalDialogProvider
import me.rerere.awara.ui.LocalMessageProvider

@Composable
fun PaginationBar(
    modifier: Modifier = Modifier,
    page: Int,
    limit: Int,
    total: Int,
    onPageChange: (Int) -> Unit,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    require(page > 0) { "Page number must be greater than 0" }

    val dialog = LocalDialogProvider.current
    val message = LocalMessageProvider.current
    val maxPage = (total + limit - 1) / limit

    Surface(
        modifier = modifier,
        tonalElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            leading?.invoke()

            Spacer(modifier = Modifier.weight(1f))

            // Current page
            Text(
                text = stringResource(R.string.pagination_current, page, maxPage),
                modifier = Modifier.clickable {
                    dialog.input(
                        title = {
                            Text("Jump to page")
                        }
                    ) {
                        val target = it.toIntOrNull()
                        if (target != null) {
                            onPageChange(target)
                        } else {
                            message.error {
                                Text("Invalid page number: $it")
                            }
                        }
                    }
                }
            )

            // Previous page
            FilledTonalIconButton(
                onClick = {
                    if (page > 1) {
                        onPageChange(page - 1)
                    }
                },
            ) {
                Icon(Icons.Outlined.KeyboardArrowLeft, "Previous page")
            }

            // Next page
            FilledTonalIconButton(
                onClick = {
                    if (page < maxPage) {
                        onPageChange(page + 1)
                    }
                }
            ) {
                Icon(Icons.Outlined.KeyboardArrowRight, "Next page")
            }

            trailing?.invoke()
        }
    }
}

@Preview
@Composable
private fun PaginationPreview() {
    PaginationBar(page = 1, limit = 24, total = 43, onPageChange = {})
}