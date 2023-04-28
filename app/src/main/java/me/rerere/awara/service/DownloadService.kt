package me.rerere.awara.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

private const val TAG = "DownloadService"

class DownloadService : Service() {
    override fun onBind(intent: Intent?): IBinder = DownloadBinder()
    inner class DownloadBinder : Binder()
}