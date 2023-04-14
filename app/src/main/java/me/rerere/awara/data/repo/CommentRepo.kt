package me.rerere.awara.data.repo

import me.rerere.awara.data.entity.CommentCreationDto
import me.rerere.awara.data.source.IwaraAPI

class CommentRepo(private val iwaraAPI: IwaraAPI) {
    suspend fun getVideoComments(
        id: String,
        page: Int
    ) = iwaraAPI.getVideoComments(id, mapOf(
        "page" to page.toString()
    ))

    suspend fun getVideoCommentReplies(
        id: String,
        page: Int,
        parent: String
    ) = iwaraAPI.getVideoComments(id, mapOf(
        "page" to page.toString(),
        "parent" to parent
    ))

    suspend fun postVideoComment(
        id: String,
        content: String,
        parent: String?
    ) = iwaraAPI.postVideoComment(id, CommentCreationDto(
        body = content,
        parentId = parent
    ))
}