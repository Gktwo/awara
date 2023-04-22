package me.rerere.awara.data.source

import me.rerere.awara.util.await
import okhttp3.OkHttpClient
import okhttp3.Request

class UpdateChecker(private val okHttpClient: OkHttpClient) {
    suspend fun getLatestVersion(): VersionMeta {
        val req = Request.Builder()
            .url("https://cdn.jsdelivr.net/gh/re-ovo/awara/app/build.gradle.kts")
            .get()
            .build()
        val resp = okHttpClient.newCall(req).await()
        val body = resp.body?.string() ?: throw Exception("No response body")

        // Get version code(versionCode = 1)
        val versionCode = Regex("versionCode = (\\d+)").find(body)?.groupValues?.get(1)?.toInt()
            ?: throw Exception("No version code found")

        // Get version name(versionName = "1.0.0")
        val versionName = Regex("versionName = \"(\\d+\\.\\d+\\.\\d+)\"").find(body)?.groupValues?.get(1)
            ?: throw Exception("No version name found")

        // Get changes
        val changes = getChanges()

        return VersionMeta(versionCode, versionName, changes)
    }

    private suspend fun getChanges(): String {
        val req = Request.Builder()
            .url("https://cdn.jsdelivr.net/gh/re-ovo/awara/doc/changes.txt")
            .get()
            .build()
        val resp = okHttpClient.newCall(req).await()
        val body = resp.body?.string() ?: throw Exception("No response body")
        return body.trim()
    }
}

data class VersionMeta(
    val versionCode: Int,
    val versionName: String,
    val changes: String
)