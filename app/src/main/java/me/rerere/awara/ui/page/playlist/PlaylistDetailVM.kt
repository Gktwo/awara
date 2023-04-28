package me.rerere.awara.ui.page.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class PlaylistDetailVM(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id = checkNotNull(savedStateHandle.get<String>("id"))


}