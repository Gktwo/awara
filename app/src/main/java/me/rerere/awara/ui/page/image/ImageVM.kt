package me.rerere.awara.ui.page.image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.Image
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching

class ImageVM(
    savedStateHandle: SavedStateHandle,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    private val id = checkNotNull(savedStateHandle.get<String>("id"))

    var state by mutableStateOf(ImageState())
        private set

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            runAPICatching {
                mediaRepo.getImage(id)
            }.onSuccess {
                state = state.copy(state = it)
            }
            state = state.copy(loading = false)
        }
    }

    data class ImageState(
        val loading: Boolean = false,
        val state: Image? = null,
    )
}