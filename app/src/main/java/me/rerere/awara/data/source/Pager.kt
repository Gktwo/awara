package me.rerere.awara.data.source

import kotlinx.serialization.Serializable

@Serializable
data class Pager<T>(
    override val count: Int,
    override val limit: Int,
    override val page: Int,
    override val results: List<T>
): IPager<T>

interface IPager<T> {
    val count: Int
    val limit: Int
    val page: Int
    val results: List<T>
}