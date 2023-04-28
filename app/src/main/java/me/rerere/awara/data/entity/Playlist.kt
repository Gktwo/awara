package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val id: String,
    val numVideos: Int,
    val title: String,
    val user: User
)