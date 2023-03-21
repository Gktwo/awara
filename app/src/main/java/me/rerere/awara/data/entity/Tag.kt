package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val id: String,
    val type: String
)