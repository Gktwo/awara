package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable
import me.rerere.awara.util.InstantSerializer
import java.time.Instant

@Serializable
data class User(
    val id: String,
    val username: String,
    val name: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val deletedAt: Instant?,
    val followedBy: Boolean,
    val following: Boolean,
    val friend: Boolean,
    val premium: Boolean,
    val role: String,
    @Serializable(with = InstantSerializer::class)
    val seenAt: Instant?,
    val status: String,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
    val avatar: File?,
)
