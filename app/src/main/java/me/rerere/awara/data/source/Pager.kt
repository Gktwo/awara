package me.rerere.awara.data.source

import kotlinx.serialization.Serializable

@Serializable
data class Pager<T>(
    val count: Int,
    val limit: Int,
    val page: Int,
    val results: List<T>
)