package me.rerere.awara.data.repo

import me.rerere.awara.data.source.IwaraAPI

class MediaRepo(
    private val iwaraAPI: IwaraAPI
){
    suspend fun getVideoList(
        queryMap: Map<String, String>
    ) = iwaraAPI.getVideoList(queryMap)

    suspend fun getImageList(
        queryMap: Map<String, String>
    ) = iwaraAPI.getImageList(queryMap)
}