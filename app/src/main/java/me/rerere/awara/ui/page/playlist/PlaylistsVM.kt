package me.rerere.awara.ui.page.playlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.Playlist
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching

class PlaylistsVM(
    savedStateHandle: SavedStateHandle,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    private val userId = checkNotNull(savedStateHandle.get<String>("userId"))
    var state by mutableStateOf(PlaylistsState())
        private set

    init {
        loadPlaylist()
    }

    fun jumpToPage(page: Int) {
        state = state.copy(
            page = page
        )
        loadPlaylist()
    }

    private fun loadPlaylist() {
        viewModelScope.launch {
            state = state.copy(
                loading = true
            )
            runAPICatching {
                mediaRepo.getPlaylists(
                    userId = userId,
                    page = state.page - 1,
                )
            }.onSuccess {
                state = state.copy(
                    list = it.results,
                    count = it.count
                )
            }
            state = state.copy(
                loading = false
            )
        }
    }

    data class PlaylistsState(
        val loading: Boolean = false,
        val page: Int = 1,
        val count: Int = 0,
        val list: List<Playlist> = emptyList()
    )
}