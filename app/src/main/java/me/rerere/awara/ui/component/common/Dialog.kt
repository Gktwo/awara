package me.rerere.awara.ui.component.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R

@Composable
fun DialogProvider(
    content: @Composable () -> Unit
) {
    val holder = remember {
        DialogHolder()
    }
    CompositionLocalProvider(
        LocalDialogProvider provides holder
    ) {
        content()
    }
    holder.dialogs.forEach { dialog ->
        dialog.render {
            holder.dialogs.remove(dialog)
        }
    }
}

data class DialogRequest(
    val render: @Composable (dismiss: () -> Unit) -> Unit,
)

val LocalDialogProvider = staticCompositionLocalOf<DialogHolder> {
    error("DialogProvider not found")
}

class DialogHolder {
    val dialogs = mutableStateListOf<DialogRequest>()

    fun show(
        title: (@Composable () -> Unit)? = null,
        positiveText: (@Composable () -> Unit)? = null,
        positiveAction: (() -> Unit)? = null,
        negativeText: (@Composable () -> Unit)? = null,
        negativeAction: (() -> Unit)? = null,
        content: (@Composable () -> Unit)? = null,
    ) {
        dialogs.add(
            DialogRequest { dismiss ->
                AlertDialog(
                    onDismissRequest = {
                        dismiss()
                    },
                    title = {
                        title?.invoke()
                    },
                    text = {
                        content?.invoke()
                    },
                    confirmButton = {
                        positiveText?.let {
                            TextButton(
                                onClick = {
                                    dismiss()
                                    positiveAction?.invoke()
                                }
                            ) {
                                it.invoke()
                            }
                        }
                    },
                    dismissButton = {
                        negativeText?.let {
                            TextButton(
                                onClick = {
                                    dismiss()
                                    negativeAction?.invoke()
                                }
                            ) {
                                it.invoke()
                            }
                        }
                    }
                )
            }
        )
    }

    fun input(
        title: (@Composable () -> Unit)? = null,
        confirm: (String) -> Unit,
    ) {
        dialogs.add(
            DialogRequest { dismiss ->
                var content by remember {
                    mutableStateOf("")
                }
                AlertDialog(
                    onDismissRequest = {
                        dismiss()
                    },
                    title = {
                        title?.invoke()
                    },
                    text = {
                        OutlinedTextField(
                            value = content,
                            onValueChange = {
                                content = it
                            }
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                dismiss()
                                confirm(content)
                            }
                        ) {
                            Text(stringResource(R.string.confirm))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                dismiss()
                            }
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                )
            }
        )
    }
}