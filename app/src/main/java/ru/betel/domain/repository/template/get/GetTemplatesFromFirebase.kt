package ru.betel.domain.repository.template.get

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate

interface GetTemplatesFromFirebase {
    fun getTemplates(): Flow<List<SongTemplate>>
}