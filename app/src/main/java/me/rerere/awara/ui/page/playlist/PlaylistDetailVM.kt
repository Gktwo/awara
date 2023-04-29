package me.rerere.awara.ui.page.playlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.Playlist
import me.rerere.awara.data.entity.Video
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching

class PlaylistDetailVM(
    savedStateHandle: SavedStateHandle,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    val id = checkNotNull(savedStateHandle.get<String>("id"))
    var state by mutableStateOf(PlaylistDetailState())
        private set

    init {
        loadPlaylistDetail()
    }

    fun jumpToPage(page: Int) {
        state = state.copy(page = page)
        loadPlaylistDetail()
    }

    private fun loadPlaylistDetail() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            runAPICatching {
                mediaRepo.getPlaylistContent(
                    playlistId = id,
                    page = state.page - 1
                )
            }.onSuccess {
                state = state.copy(
                    count = it.count,
                    list = it.results,
                    playlist = it.playlist
                )
            }
            state = state.copy(loading = false)
        }
    }

    data class PlaylistDetailState(
        val loading: Boolean = false,
        val page: Int = 1,
        val count: Int = 0,
        val list: List<Video> = emptyList(),
        val playlist: Playlist? = null,
    )
}