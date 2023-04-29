package me.rerere.awara.data.entity

import kotlinx.serialization.Serializable
import me.rerere.awara.util.InstantSerializer
import me.rerere.awara.util.sha1
import me.rerere.awara.util.toHex
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.time.Instant

sealed interface Media {
    val id: String
    val title: String
    val liked: Boolean
    val numComments: Int
    val numLikes: Int
    val numViews: Int
    val createdAt: Instant
    val updatedAt: Instant
    val deletedAt: Instant?
    val slug: String?
    val user: User
    val body: String?
    val tags: List<Tag>
}

@Serializable
data class Video(
    override val id: String,
    override val title: String,
    override val liked: Boolean,
    override val numComments: Int,
    override val numLikes: Int,
    override val numViews: Int,
    override val body: String?,
    @Serializable(with = InstantSerializer::class)
    override val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    override val updatedAt: Instant,
    @Serializable(with = InstantSerializer::class)
    override val deletedAt: Instant?,
    override val slug: String?,
    override val user: User,
    override val tags: List<Tag>,
    val private: Boolean,
    val rating: String,
    val status: String,
    val embedUrl: String?,
    val file: File?,
    val fileUrl: String?,
    val thumbnail: Int
): Media

@Serializable
data class Image(
    override val id: String,
    override val title: String,
    override val liked: Boolean,
    override val numComments: Int,
    override val numLikes: Int,
    override val numViews: Int,
    override val body: String?,
    @Serializable(with = InstantSerializer::class)
    override val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    override val updatedAt: Instant,
    @Serializable(with = InstantSerializer::class)
    override val deletedAt: Instant?,
    override val slug: String?,
    override val user: User,
    override val tags: List<Tag>,
    val numImages: Int,
    val rating: String,
    val files: List<File>,
    val thumbnail: File
): Media

fun Media.thumbnailUrl(): String = when(this) {
    is Video -> if (embedUrl != null) {
        val url = embedUrl.toHttpUrlOrNull()
        val id = url?.queryParameter("v") ?: url?.pathSegments?.lastOrNull() ?: ""
        "https://i.iwara.tv/image/embed/thumbnail/youtube/$id"
    } else run {
        val id = this.thumbnail.toString().padStart(2, '0')
        "https://i.iwara.tv/image/thumbnail/${file?.id}/thumbnail-$id.jpg"
    }
    is Image -> "https://i.iwara.tv/image/thumbnail/${thumbnail.id}/${thumbnail.name}"
}

val Video.signature: String
    get() {
        val url = fileUrl?.toHttpUrl() ?: return ""
        val id = url.pathSegments.lastOrNull() ?: return ""
        val expire = url.queryParameter("expires") ?: return ""
        return "${id}_${expire}_5nFp9kmbNnHdAFhaqMvt".toByteArray().sha1().toHex().lowercase()
    }