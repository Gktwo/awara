package me.rerere.awara.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri

/**
 * Open the url in browser
 *
 * @receiver Context
 * @param url String
 */
fun Context.openUrl(url: String) {
    if(url.matches(Regex("https?://.*"))) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

/**
 * Find the activity from the context
 *
 * @receiver Context
 * @return Activity
 * @throws IllegalStateException if the context is not an activity
 */
fun Context.findActivity(): Activity = when (this) {
    is Activity -> this
    is ContextWrapper -> {
        baseContext.findActivity()
    }
    else -> throw IllegalStateException("Context is not an Activity")
}