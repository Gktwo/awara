package me.rerere.awara.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRes(
    val token: String,
)

@Serializable
data class LoginReq(
    val email: String,
    val password: String,
)

@Serializable
data class AccessTokenRes(
    val accessToken: String
)