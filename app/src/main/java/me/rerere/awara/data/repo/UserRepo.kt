package me.rerere.awara.data.repo

import me.rerere.awara.data.dto.LoginReq
import me.rerere.awara.data.source.IwaraAPI

class UserRepo(private val iwaraAPI: IwaraAPI) {
    suspend fun login(
        email: String,
        password: String
    ) = iwaraAPI.login(
        LoginReq(
            email = email,
            password = password
        )
    )

    suspend fun renewToken() = iwaraAPI.renewToken()
}