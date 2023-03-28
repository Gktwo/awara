package me.rerere.awara.ui.page.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class VideoVM(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id = checkNotNull(savedStateHandle.get<String>("id"))
}