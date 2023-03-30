package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class VideoFile(
    val id: String,
    val name: String,
    val type: String,
    val src: VideoSrc
)

@Serializable
data class VideoSrc(
    val download: String,
    val view: String,
)

fun String.fixUrl() = if (startsWith("//")) "https:$this" else this