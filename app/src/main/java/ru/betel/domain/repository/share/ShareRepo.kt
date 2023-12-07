package ru.betel.domain.repository.share

import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate

interface ShareRepo {
    suspend fun shareSong(song: Song)

    suspend fun shareSongTemplate(template: SongTemplate)
}