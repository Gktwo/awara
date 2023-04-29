package me.rerere.awara.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.util.Log

private const val TAG = "ContextUtil"

val Context.versionCode: Int
    get() = packageManager.getPackageInfo(packageName, 0).versionCode

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
    } else {
        Log.w(TAG, "openUrl: url is not valid: $url")
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

fun Context.writeToClipboard(label: String, text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}

fun Context.shareLink(url: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, url)
    intent.type = "text/plain"
    startActivity(Intent.createChooser(intent, "Share"))
}