package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable
import me.rerere.awara.util.InstantSerializer
import java.time.Instant

@Serializable
data class FavoriteVideo(
    val id: Int,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    val user: User,
    val video: Video,
)

@Serializable
data class FavoriteImage(
    val id: Int,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    val user: User,
    val image: Image,
)