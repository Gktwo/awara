package me.rerere.awara.data.source

import me.rerere.awara.data.entity.Hitokoto
import retrofit2.http.GET
import retrofit2.http.Query

interface HitokotoAPI {
    @GET("/")
    suspend fun getHitokoto(
        @Query("c") type: String = "a"
    ): Hitokoto
}