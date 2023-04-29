package me.rerere.awara.ui.page.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.FavoriteImage
import me.rerere.awara.data.entity.FavoriteVideo
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching

class FavoritesVM(
    private val mediaRepo: MediaRepo
) : ViewModel() {
    var state by mutableStateOf(FavoritesState())
        private set

    init {
        loadVideo()
        loadImages()
    }

    fun jumpToVideoPage(page: Int) {
        state = state.copy(videoPage = page)
        loadVideo()
    }

    fun jumpToImagePage(page: Int) {
        state = state.copy(imagePage = page)
        loadImages()
    }

    private fun loadVideo() {
        viewModelScope.launch {
            state = state.copy(videoLoading = true)
            runAPICatching {
                mediaRepo.getFavoriteVideos(state.videoPage - 1)
            }.onSuccess {
                state = state.copy(
                    videoLoading = false,
                    videoCount = it.count,
                    videoList = it.results
                )
            }
            state = state.copy(videoLoading = false)
        }
    }

    private fun loadImages() {
        viewModelScope.launch {
            state = state.copy(imageLoading = true)
            runAPICatching {
                mediaRepo.getFavoriteImages(state.imagePage - 1)
            }.onSuccess {
                state = state.copy(
                    imageLoading = false,
                    imageCount = it.count,
                    imageList = it.results
                )
            }
            state = state.copy(imageLoading = false)
        }
    }

    data class FavoritesState(
        val videoLoading: Boolean = false,
        val videoPage: Int = 1,
        val videoCount: Int = 0,
        val videoList: List<FavoriteVideo> = emptyList(),
        val imageLoading: Boolean = false,
        val imagePage: Int = 1,
        val imageCount: Int = 0,
        val imageList: List<FavoriteImage> = emptyList(),
    )
}