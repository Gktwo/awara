package me.rerere.awara.data.repo

import kotlinx.serialization.decodeFromString
import me.rerere.awara.data.entity.Video
import me.rerere.awara.data.entity.VideoFile
import me.rerere.awara.data.entity.signature
import me.rerere.awara.data.source.IwaraAPI
import me.rerere.awara.util.JsonInstance
import me.rerere.awara.util.await
import okhttp3.OkHttpClient
import okhttp3.Request

class MediaRepo(
    private val okHttpClient: OkHttpClient,
    private val iwaraAPI: IwaraAPI
){
    suspend fun getVideoList(
        queryMap: Map<String, String>
    ) = iwaraAPI.getVideoList(queryMap)

    suspend fun getImageList(
        queryMap: Map<String, String>
    ) = iwaraAPI.getImageList(queryMap)

    suspend fun getVideo(
        id: String
    ) = iwaraAPI.getVideo(id)

    suspend fun parseVideoUrl(
        video: Video
    ): List<VideoFile> {
        val hash = video.signature
        val request = Request.Builder()
            .url(video.fileUrl ?: error("No file url"))
            .header("x-version", hash)
            .get()
            .build()
        val response = okHttpClient.newCall(request).await()
        val body = response.body ?: error("No body")
        val bodyString = body.string()
        return JsonInstance.decodeFromString(bodyString)
    }

    suspend fun getRelatedVideos(id: String) = iwaraAPI.getRelatedVideo(id)

    suspend fun likeVideo(id: String) = iwaraAPI.likeVideo(id)

    suspend fun unlikeVideo(id: String) = iwaraAPI.unlikeVideo(id)
}