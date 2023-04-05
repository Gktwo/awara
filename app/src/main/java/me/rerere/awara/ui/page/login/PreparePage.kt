package me.rerere.awara.ui.page.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.stores.LocalUserStore
import me.rerere.awara.ui.stores.collectAsState
import me.rerere.compose_setting.preference.mmkvPreference

@Composable
fun PreparePage() {
    val userStoreState = LocalUserStore.current.collectAsState()
    val router = LocalRouterProvider.current
    LaunchedEffect(userStoreState.refreshing) {
        if(!userStoreState.refreshing) {
            router.navigate("index")
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
    ){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

fun shouldShowPreparePage(): Boolean {
    return !mmkvPreference.getString("refresh_token", "").isNullOrBlank()
}