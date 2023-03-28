package me.rerere.awara.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

private const val TAG = "DownloadService"

class DownloadService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { startIntent ->
            val url = startIntent.getStringExtra("url")
            val name = startIntent.getStringExtra("name")
            val type = startIntent.getStringExtra("type")
            // TODO: Download
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder = DownloadBinder()

    inner class DownloadBinder : Binder() {

    }
}