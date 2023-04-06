package me.rerere.awara.ui.stores

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import me.rerere.awara.data.dto.ProfileDto
import me.rerere.awara.data.entity.Tag
import me.rerere.awara.data.entity.User
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.onError
import me.rerere.awara.data.source.onException
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching
import me.rerere.compose_setting.preference.mmkvPreference
import me.rerere.compose_setting.preference.rememberStringPreference
import org.koin.androidx.compose.get
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val TAG = "UserStore"

val LocalUserStore = staticCompositionLocalOf {
    ReduxStore(
        initialState = UserStoreState(),
        reducer = object : Reducer<UserStoreState, UserStoreAction>, KoinComponent {
            // private val userRepo by inject<UserRepo>()
            override fun reduce(
                prevState: UserStoreState,
                action: UserStoreAction
            ): UserStoreState {
                return when (action) {
                    is UserStoreAction.SetUser -> prevState.copy(user = action.user)
                    is UserStoreAction.SetTagBlacklist -> prevState.copy(tagBlacklist = action.tagBlacklist)
                    is UserStoreAction.Logout -> {
                        mmkvPreference.remove("refresh_token")
                        mmkvPreference.remove("access_token")
                        prevState.copy(
                            user = null,
                            tagBlacklist = emptyList(),
                            profile = null,
                            loading = false
                        )
                    }

                    is UserStoreAction.SetProfile -> {
                        prevState.copy(profile = action.profile)
                    }

                    is UserStoreAction.SetLoading -> {
                        prevState.copy(loading = action.loading)
                    }

                    is UserStoreAction.SetRefreshing -> {
                        prevState.copy(refreshing = action.refreshing)
                    }
                }
            }
        }
    )
}

data class UserStoreState(
    val refreshing: Boolean = false,
    val loading: Boolean = false,
    val user: User? = null,
    val profile: ProfileDto? = null,
    val tagBlacklist: List<Tag> = emptyList()
)

sealed class UserStoreAction {
    data class SetUser(val user: User) : UserStoreAction()
    data class SetTagBlacklist(val tagBlacklist: List<Tag>) : UserStoreAction()
    data class SetProfile(val profile: ProfileDto) : UserStoreAction()
    data class SetLoading(val loading: Boolean) : UserStoreAction()
    data class SetRefreshing(val refreshing: Boolean) : UserStoreAction()
    object Logout : UserStoreAction()
}

@Composable
fun UserStoreProvider(
    userRepo: UserRepo = get(),
    content: @Composable () -> Unit
) {
    val store = LocalUserStore.current
    val refreshToken by rememberStringPreference(key = "refresh_token", default = "")
    val accessToken by rememberStringPreference(key = "access_token", default = "")
    // 尝试刷新AccessToken
    LaunchedEffect(refreshToken) {
        if (refreshToken.isNotEmpty()) {
            Log.i(TAG, "UserStoreProvider: renew access token start")
            store.dispatch(UserStoreAction.SetRefreshing(true))
            runAPICatching {
                delay(100)
                userRepo.renewToken()
            }.onSuccess {
                mmkvPreference.putString("access_token", it.accessToken)
                Log.i(TAG, "UserStoreProvider: renew access token success")
            }.onError {
                Log.w(TAG, "UserStoreProvider: renew access token error: $it")
            }.onException {
                Log.w(TAG, "UserStoreProvider: renew access token exception", it.exception)
            }
            store.dispatch(UserStoreAction.SetRefreshing(false))
            Log.i(TAG, "UserStoreProvider: finish renew access token")
        }
    }
    // 当Token变化时，重新获取用户信息
    LaunchedEffect(accessToken) {
        if (accessToken.isEmpty()) return@LaunchedEffect
        Log.i(TAG, "UserStoreProvider: get user info")
        store.dispatch(UserStoreAction.SetLoading(true))
        awaitAll(
            async {
                runAPICatching {
                    userRepo.getSelfProfile()
                }.onSuccess {
                    store.dispatch(UserStoreAction.SetUser(it.user))
                    store.dispatch(UserStoreAction.SetTagBlacklist(it.tagBlacklist))
                    store.dispatch(UserStoreAction.SetProfile(it.profile))
                    Log.i(TAG, "UserStoreProvider: get user info success: $it")
                }.onError {
                    Log.e(TAG, "UserStoreProvider: get user info error: $it")
                }.onException {
                    Log.e(TAG, "UserStoreProvider: get user info exception", it.exception)
                }
            }
        )
        store.dispatch(UserStoreAction.SetLoading(false))
    }
    content()
}