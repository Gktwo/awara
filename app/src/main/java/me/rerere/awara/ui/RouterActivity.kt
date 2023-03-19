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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import me.rerere.awara.ui.component.common.MessageProvider
import me.rerere.awara.ui.page.index.IndexPage
import me.rerere.awara.ui.page.login.LoginPage
import me.rerere.awara.ui.theme.AwaraTheme


class RouterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            AwaraTheme {
                CompositionLocalProvider(
                    LocalRouterProvider provides navController
                ) {
                    MessageProvider {
                        AnimatedNavHost(
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
                        }
                    }
                }
            }
        }
    }
}