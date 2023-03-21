package me.rerere.awara.data.source

import me.rerere.awara.data.dto.LoginReq
import me.rerere.awara.data.dto.LoginRes
import me.rerere.awara.data.dto.ProfileDto
import me.rerere.awara.data.dto.Self
import me.rerere.awara.data.entity.Image
import me.rerere.awara.data.entity.Video
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface IwaraAPI {
    @POST("/user/login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): LoginRes

    @POST("/user/token")
    suspend fun renewToken(): LoginRes

    @GET("/user")
    suspend fun getSelfProfile(): Self

    @GET("/profile/{username}")
    suspend fun getProfile(
        @Path("username") username: String
    ): ProfileDto

    @GET("/videos")
    suspend fun getVideoList(
        @QueryMap queryMap: Map<String, String>
    ): Pager<Video>

    @GET("/images")
    suspend fun getImageList(
        @QueryMap queryMap: Map<String, String>
    ): Pager<Image>

}