package me.rerere.awara.data.dto

import kotlinx.serialization.Serializable
import me.rerere.awara.data.entity.File
import me.rerere.awara.data.entity.User
import me.rerere.awara.util.InstantSerializer
import java.time.Instant

@Serializable
data class ProfileDto(
    val user: User,
    val header: File?,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
    val body: String?,
)