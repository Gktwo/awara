package me.rerere.awara.data.dto

import kotlinx.serialization.Serializable
import me.rerere.awara.data.entity.Tag
import me.rerere.awara.data.entity.User

@Serializable
data class Self(
    val tagBlacklist: List<Tag>,
    val user: User,
    val profile: ProfileDto
)