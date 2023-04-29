package me.rerere.awara.ui.page.image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.HistoryItem
import me.rerere.awara.data.entity.HistoryType
import me.rerere.awara.data.entity.Image
import me.rerere.awara.data.entity.thumbnailUrl
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching
import me.rerere.awara.di.AppDatabase
import java.time.Instant

class ImageVM(
    savedStateHandle: SavedStateHandle,
    private val mediaRepo: MediaRepo,
    private val appDatabase: AppDatabase
) : ViewModel() {
    val id = checkNotNull(savedStateHandle.get<String>("id"))

    var state by mutableStateOf(ImageState())
        private set

    init {
        load()
    }

    fun likeOrDislike() {
        viewModelScope.launch {
            state = state.copy(likeLoading = true)
            runAPICatching {
                if(state.state?.liked == true) {
                    mediaRepo.unlikeImage(id)
                } else {
                    mediaRepo.likeImage(id)
                }

                mediaRepo.getImage(id).let {
                    state = state.copy(state = it)
                }
            }
            state = state.copy(likeLoading = false)
        }
    }

    private fun load() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            runAPICatching {
                mediaRepo.getImage(id)
            }.onSuccess {
                state = state.copy(state = it)
                writeHistory()
            }
            state = state.copy(loading = false)
        }
    }

    private fun writeHistory() {
        viewModelScope.launch {
            kotlin.runCatching {
                appDatabase.historyDao().insertHistory(
                    HistoryItem(
                        time = Instant.now(),
                        type = HistoryType.IMAGE,
                        resourceId = id,
                        title = state.state?.title ?: "",
                        thumbnail = state.state?.thumbnailUrl() ?: ""
                    )
                )
            }.onFailure {
                it.printStackTrace()
            }
        }
    }


    data class ImageState(
        val loading: Boolean = false,
        val likeLoading: Boolean = false,
        val state: Image? = null,
    )
}