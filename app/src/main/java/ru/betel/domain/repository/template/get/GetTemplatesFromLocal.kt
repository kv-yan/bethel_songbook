package ru.betel.domain.repository.template.get

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.entity.SongTemplateEntity

interface GetTemplatesFromLocal {
    fun getTemplates(): Flow<List<SongTemplateEntity>>
}