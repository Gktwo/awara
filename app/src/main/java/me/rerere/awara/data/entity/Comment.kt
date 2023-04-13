package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable
import me.rerere.awara.util.InstantSerializer
import java.time.Instant

@Serializable
data class Comment(
    val body: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
    val id: String,
    val numReplies: Int,
    val user: User?,
    val parent: Comment? = null,
)