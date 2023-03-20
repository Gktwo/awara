package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class File(
    val id: String,
    val mime: String,
    val name: String,
    val path: String,
    val type: String,
)