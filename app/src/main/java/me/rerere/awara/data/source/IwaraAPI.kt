package me.rerere.awara.data.source

import me.rerere.awara.data.dto.AccessTokenRes
import me.rerere.awara.data.dto.LoginReq
import me.rerere.awara.data.dto.LoginRes
import me.rerere.awara.data.dto.ProfileDto
import me.rerere.awara.data.dto.Self
import me.rerere.awara.data.entity.Comment
import me.rerere.awara.data.entity.CommentCreationDto
import me.rerere.awara.data.entity.Image
import me.rerere.awara.data.entity.Video
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    suspend fun renewToken(): AccessTokenRes

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

    @GET("/video/{id}")
    suspend fun getVideo(
        @Path("id") id: String
    ): Video

    @GET("/video/{id}/related")
    suspend fun getRelatedVideo(
        @Path("id") id: String
    ): Pager<Video>

    @POST("/video/{id}/like")
    suspend fun likeVideo(
        @Path("id") id: String
    ): Response<Unit>

    @DELETE("/video/{id}/like")
    suspend fun unlikeVideo(
        @Path("id") id: String
    ): Response<Unit>

    @GET("/video/{id}/comments")
    suspend fun getVideoComments(
        @Path("id") id: String,
        @QueryMap queryMap: Map<String, String>
    ): Pager<Comment>

    @POST("/video/{id}/comments")
    suspend fun postVideoComment(
        @Path("id") id: String,
        @Body comment: CommentCreationDto
    ): Comment
}