package me.rerere.awara.ui.stores

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.Tag
import me.rerere.awara.data.entity.User
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching
import me.rerere.compose_setting.preference.mmkvPreference
import me.rerere.compose_setting.preference.rememberStringPreference
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserStoreProvider(
    vm: UserStoreVM = koinViewModel(),
    content: @Composable () -> Unit
) {
    val token by rememberStringPreference(key = "token", default = "")
    LaunchedEffect(token) {
        if (token.isNotBlank()) {
            vm.init()
        }
    }
    CompositionLocalProvider(
        LocalUserStore provides vm.userStore
    ) {
        content()
    }
}

val LocalUserStore = staticCompositionLocalOf<UserStore> {
    error("UserStore not found")
}

data class UserStore(
    val user: User? = null,
    val tagBlacklist: List<Tag> = emptyList()
) {
    val isLogin: Boolean
        get() = user != null
}

class UserStoreVM(
    private val userRepo: UserRepo
) : ViewModel() {
    var userStore by mutableStateOf(UserStore())

    fun init() {
        viewModelScope.launch {
            awaitAll(
                async {
                    runAPICatching {
                        userRepo.getSelfProfile()
                    }.onSuccess {
                        userStore = UserStore(
                            user = it.user,
                            tagBlacklist = it.tagBlacklist
                        )
                    }
                }, async {
                    runAPICatching {
                        userRepo.renewToken()
                    }.onSuccess {
                        mmkvPreference.putString("token", it.token)
                    }
                }
            )
        }
    }
}