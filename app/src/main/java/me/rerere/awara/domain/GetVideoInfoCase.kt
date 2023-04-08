package me.rerere.awara.domain

import me.rerere.awara.data.repo.MediaRepo

class GetVideoInfoCase(
    private val mediaRepo: MediaRepo
) {
    suspend operator fun invoke(id: String) {
        val info = mediaRepo.getVideo(id)
        // TODO: Do something with info
    }
}