package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable
import me.rerere.awara.util.InstantSerializer
import java.time.Instant

@Serializable
data class Media(
    val id: String,
    val title: String,
    val numComments: Int,
    val numLikes: Int,
    val numViews: Int,
    val private: Boolean,
    val rating: String,
    val status: String,
    val slug: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val deletedAt: Instant?,
    val user: User,
    val file: File?,
    val files: List<File>?,
)