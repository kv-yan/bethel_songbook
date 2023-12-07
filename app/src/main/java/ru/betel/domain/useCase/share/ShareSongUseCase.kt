package ru.betel.domain.useCase.share

import ru.betel.domain.model.Song
import ru.betel.domain.repository.share.ShareRepo

class ShareSongUseCase(private val shareRepo: ShareRepo) {
    suspend fun execute(song: Song) {
        shareRepo.shareSong(song)
    }
}