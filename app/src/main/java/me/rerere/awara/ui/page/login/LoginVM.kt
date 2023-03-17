package me.rerere.awara.ui.page.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.repo.UserRepo

class LoginVM(private val userRepo: UserRepo) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            state = state.copy(
                loading = true
            )
            kotlin.runCatching {
                userRepo.login(username, password)
            }.onSuccess {
                // TODO: Handle success
            }.onFailure {
                // TODO: Handle error
            }
        }
    }
}

data class LoginState(
    val loading: Boolean = false,
)

