package me.rerere.awara.ui.component.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import net.mm2d.upnp.Device

@Composable
fun DlnaSelector(
    show: Boolean,
    onDismiss: () -> Unit,
    state: DlnaCastState,
    onDeviceSelected: (Device) -> Unit
) {
    if (show) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Select a device",
                    style = MaterialTheme.typography.titleLarge,
                )
                Column(
                    modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (state.deviceList.isNotEmpty()) {
                        state.deviceList.fastForEach {
                            Card(
                                onClick = {
                                    onDeviceSelected(it)
                                    onDismiss()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = it.friendlyName,
                                        style = MaterialTheme.typography.titleMedium,
                                    )

                                    Text(
                                        text = it.ipAddress,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "No device found",
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
                Text(
                    text = "Note: Only support DLNA devices",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}