package me.rerere.awara.ui.page.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginVM : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set
}

data class LoginState(
    val username: String = "",
    val password: String = "",
)

