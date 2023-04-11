package me.rerere.awara.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import me.rerere.awara.util.writeToClipboard

class ErrorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        val error = intent.getStringExtra("error")

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Oops, App Crashed~") },
                        actions = {
                            IconButton(
                                onClick = {
                                    writeToClipboard("error", error ?: "No error message")
                                }
                            ) {
                                Icon(Icons.Outlined.CopyAll, "Copy")
                            }
                        }
                    )
                }
            ) {
                Text(
                    text = error ?: "No error message",
                    modifier = Modifier
                        .padding(it)
                        .verticalScroll(rememberScrollState()),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

fun Application.registerErrorHandler() {
    Thread.setDefaultUncaughtExceptionHandler { t, e ->
        e.printStackTrace()
        val intent = Intent(this, ErrorActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("error", e.stackTraceToString())
            }
        startActivity(intent)
    }
}