package me.rerere.awara.ui.page.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.dto.ProfileDto
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.runAPICatching

class UserVM(
    savedStateHandle: SavedStateHandle,
    private val userRepo: UserRepo
) : ViewModel() {
    val id = checkNotNull(savedStateHandle.get<String>("id"))
    var state by mutableStateOf(UserVMState())

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            runAPICatching {
                state = state.copy(profile = userRepo.getProfile(id))
            }
            state = state.copy(loading = false)
        }
    }

    data class UserVMState(
        val loading: Boolean = false,
        val profile: ProfileDto? = null
    )
}