package ru.betel.domain.repository.template.set.local

import ru.betel.domain.model.SongTemplate

interface SaveTemplateToLocal {
    suspend fun saveTemplate(songTemplate: SongTemplate): Result<Unit>
}