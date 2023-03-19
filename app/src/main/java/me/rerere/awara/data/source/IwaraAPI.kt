package me.rerere.awara.data.source

import me.rerere.awara.data.dto.LoginReq
import me.rerere.awara.data.dto.LoginRes
import retrofit2.http.Body
import retrofit2.http.POST

interface IwaraAPI {
    @POST("/user/login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): LoginRes

    @POST("/user/token")
    suspend fun renewToken(): LoginRes
}