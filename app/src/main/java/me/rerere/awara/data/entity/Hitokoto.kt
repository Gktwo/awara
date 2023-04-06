package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Hitokoto(
    val hitokoto: String,
    val from: String,
)