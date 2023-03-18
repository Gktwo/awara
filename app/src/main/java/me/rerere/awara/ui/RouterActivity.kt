package me.rerere.awara.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "index"
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