package ru.betel.domain.useCase.share

import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.share.ShareRepo

class ShareSongTemplateUseCase(private val shareSongTemplateOnPlatforms: ShareRepo) {
    suspend fun execute(song: SongTemplate) {
        shareSongTemplateOnPlatforms.shareSongTemplate(song)
    }
}