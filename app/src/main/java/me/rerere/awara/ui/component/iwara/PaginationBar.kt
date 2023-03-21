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
import me.rerere.awara.ui.LocalMessageProvider
import me.rerere.awara.ui.component.common.LocalDialogProvider

@Composable
fun PaginationBar(
    modifier: Modifier = Modifier,
    page: Int,
    onPageChange: (Int) -> Unit,
    leading: @Composable (() -> Unit)? = null,
    tailing: @Composable (() -> Unit)? = null
) {
    val dialog = LocalDialogProvider.current
    val message = LocalMessageProvider.current
    Surface(
        modifier = modifier,
        tonalElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            leading?.invoke()

            Spacer(modifier = Modifier.weight(1f))

            // Current page
            Text(
                text = stringResource(R.string.pagination_current, page),
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
                    onPageChange(page - 1)
                }
            ) {
                Icon(Icons.Outlined.KeyboardArrowLeft, "Previous page")
            }

            // Next page
            FilledTonalIconButton(
                onClick = {
                    onPageChange(page + 1)
                }
            ) {
                Icon(Icons.Outlined.KeyboardArrowRight, "Next page")
            }

            tailing?.invoke()
        }
    }
}

@Preview
@Composable
private fun PaginationPreview() {
    PaginationBar(page = 1, onPageChange = {})
}