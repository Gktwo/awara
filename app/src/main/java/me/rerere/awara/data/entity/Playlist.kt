package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable
import me.rerere.awara.data.source.IPager

@Serializable
data class Playlist(
    val id: String,
    val numVideos: Int,
    val title: String,
    val user: User?,
    val added: Boolean?
)

@Serializable
data class PlaylistPager(
    override val count: Int,
    override val limit: Int,
    override val page: Int,
    override val results: List<Video>,
    val playlist: Playlist
) : IPager<Video>