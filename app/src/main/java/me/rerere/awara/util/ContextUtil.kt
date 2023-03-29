package me.rerere.awara.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}

fun Context.findActivity(): Activity = when (this) {
    is Activity -> this
    is ContextWrapper -> {
        baseContext.findActivity()
    }
    else -> throw IllegalStateException("Context is not an Activity")
}