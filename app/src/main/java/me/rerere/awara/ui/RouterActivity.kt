package me.rerere.awara.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import me.rerere.awara.ui.component.common.DialogProvider
import me.rerere.awara.ui.component.common.MessageProvider
import me.rerere.awara.ui.page.index.IndexPage
import me.rerere.awara.ui.page.login.LoginPage
import me.rerere.awara.ui.page.search.SearchPage
import me.rerere.awara.ui.page.setting.SettingPage
import me.rerere.awara.ui.stores.UserStoreProvider
import me.rerere.awara.ui.theme.AwaraTheme


class RouterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            AwaraTheme {
                CompositionLocalProvider(LocalRouterProvider provides navController) {
                    ContextProvider {
                        UserStoreProvider {
                            AnimatedNavHost(
                                modifier = Modifier
                                    .fillMaxSize()
                                    // 防止夜间模式下切换页面闪白屏
                                    .background(MaterialTheme.colorScheme.background),
                                navController = navController,
                                startDestination = "index",
                                enterTransition = {
                                    slideInVertically(
                                        initialOffsetY = { 1000 },
                                        animationSpec = tween(300)
                                    ) + fadeIn(animationSpec = tween(300))
                                },
                                exitTransition = {
                                    fadeOut(animationSpec = tween(300))
                                },
                                popEnterTransition = {
                                    fadeIn(animationSpec = tween(300))
                                },
                                popExitTransition = {
                                    slideOutVertically(
                                        targetOffsetY = { 1000 },
                                        animationSpec = tween(300)
                                    ) + fadeOut(animationSpec = tween(300))
                                }
                            ) {
                                composable("index") {
                                    IndexPage()
                                }

                                composable("login") {
                                    LoginPage()
                                }

                                composable("setting") {
                                    SettingPage()
                                }

                                composable("search") {
                                    SearchPage()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ContextProvider(
        content: @Composable () -> Unit
    ) {
        MessageProvider {
            DialogProvider {
                content()
            }
        }
    }
}