package me.rerere.awara.data.source

import me.rerere.awara.data.dto.LoginReq
import me.rerere.awara.data.dto.LoginRes
import me.rerere.awara.data.dto.ProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IwaraAPI {
    @POST("/user/login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): LoginRes

    @POST("/user/token")
    suspend fun renewToken(): LoginRes

    @GET("/profile/{username}")
    suspend fun getProfile(
        @Path("username") username: String
    ): ProfileDto
}