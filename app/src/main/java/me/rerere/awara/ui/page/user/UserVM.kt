package me.rerere.awara.ui.page.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.dto.ProfileDto
import me.rerere.awara.data.entity.Image
import me.rerere.awara.data.entity.Video
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching

class UserVM(
    savedStateHandle: SavedStateHandle,
    private val userRepo: UserRepo,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    val id = checkNotNull(savedStateHandle.get<String>("id"))
    var state by mutableStateOf(UserVMState())

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            runAPICatching {
                state = state.copy(profile = userRepo.getProfile(id))
            }.onSuccess {
                loadVideoList()
                loadImageList()
            }
            state = state.copy(loading = false)
        }
    }

    fun followOrUnfollow() {
        if (state.followLoading) {
            return
        }

        viewModelScope.launch {
            state = state.copy(followLoading = true)
            runAPICatching {
                if (state.profile?.user?.following == true) {
                    userRepo.unfollowUser(state.profile?.user?.id ?: "")
                } else {
                    userRepo.followUser(state.profile?.user?.id ?: "")
                }
                state = state.copy(profile = userRepo.getProfile(id))
            }
            state = state.copy(followLoading = false)
        }
    }

    fun changeVideoPage(page: Int) {
        state = state.copy(videoPage = page)
        loadVideoList()
    }

    fun loadVideoList() {
        viewModelScope.launch {
            state = state.copy(videoLoading = true)
            runAPICatching {
                val result = mediaRepo.getVideoList(mapOf(
                    "page" to (state.videoPage - 1).toString(),
                    "sort" to "date",
                    "user" to (state.profile?.user?.id ?: ""),
                    "limit" to "32"
                ))
                state = state.copy(
                    videoCount = result.count,
                    videoList = result.results
                )
            }
            state = state.copy(videoLoading = false)
        }
    }

    fun changeImagePage(page: Int) {
        state = state.copy(imagePage = page)
        loadImageList()
    }

    fun loadImageList() {
        viewModelScope.launch {
            state = state.copy(imageLoading = true)
            runAPICatching {
                val result = mediaRepo.getImageList(mapOf(
                    "page" to (state.imagePage - 1).toString(),
                    "sort" to "date",
                    "user" to (state.profile?.user?.id ?: ""),
                    "limit" to "32",
                ))
                state = state.copy(
                    imageCount = result.count,
                    imageList = result.results
                )
            }
            state = state.copy(imageLoading = false)
        }
    }

    data class UserVMState(
        val loading: Boolean = false,
        val followLoading: Boolean = false,
        val profile: ProfileDto? = null,
        val videoPage: Int = 1,
        val videoCount: Int = 0,
        val videoLoading: Boolean = false,
        val videoList: List<Video> = emptyList(),
        val imagePage: Int = 1,
        val imageCount: Int = 0,
        val imageLoading: Boolean = false,
        val imageList: List<Image> = emptyList(),
    )
}