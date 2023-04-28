package me.rerere.awara.ui.page.video.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.page.video.VideoVM


@Composable
fun PlaylistSheet(vm: VideoVM, onDismissRequest: () -> Unit) {
    val currentList = vm.state.playlist
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Spin(show = vm.state.playlistLoading) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .height(400.dp),
            ) {
                items(currentList) { playlist ->
                    Card {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = CenterVertically
                        ) {
                            Text(
                                text = playlist.title,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.titleLarge
                            )

                            Switch(
                                checked = playlist.added == true,
                                onCheckedChange = {
                                    if (playlist.added == true) {
                                        vm.removeVideoFromPlaylist(playlist.id)
                                    } else {
                                        vm.addVideoToPlaylist(playlist.id)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}