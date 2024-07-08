package ru.betel.domain.repository.template.delete

import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate

interface DeleteTemplateFromFirebase {
    suspend fun deleteSong(song: SongTemplate): Boolean
}