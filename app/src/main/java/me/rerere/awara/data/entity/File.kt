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

fun File?.toHeaderUrl(): String {
    return if(this != null) {
        "https://files.iwara.tv/image/profileHeader/$id/$name"
    } else {
        "https://iwara.tv/images/default-background.jpg"
    }
}

fun File?.toAvatarUrl(): String {
    return if(this != null) {
        "https://files.iwara.tv/image/avatar/$id/$name"
    } else {
        "https://iwara.tv/images/default-avatar.jpg"
    }
}