package me.rerere.awara.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}