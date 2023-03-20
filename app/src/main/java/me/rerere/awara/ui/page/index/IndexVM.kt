package me.rerere.awara.ui.page.index

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.Image
import me.rerere.awara.data.entity.Media
import me.rerere.awara.data.entity.Video
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.APIResult
import me.rerere.awara.data.source.Pager
import me.rerere.awara.data.source.onError
import me.rerere.awara.data.source.onException
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching

private const val TAG = "IndexVM"

class IndexVM(
    private val userRepo: UserRepo,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    var state by mutableStateOf(IndexState())
        private set

    fun test() {
        viewModelScope.launch {
            runAPICatching {
                Log.i(TAG, "test: start")
                mediaRepo.getVideoList(mapOf(
                    "sort" to "date",
                    "rating" to "general"
                ))
            }.onSuccess {
                Log.i(TAG, state.toString())
                state = state.copy(videos = it)
            }.onError {
                Log.d(TAG, "init: $it")
            }.onException {
                Log.e(TAG, "test: $it")
            }
        }
    }

    init {
        test()
    }
}

data class IndexState(
    val videos: Pager<Video>? = null,
    val images: Pager<Image>? = null
)