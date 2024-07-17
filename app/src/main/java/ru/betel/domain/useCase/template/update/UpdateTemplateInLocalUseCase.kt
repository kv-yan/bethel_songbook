package ru.betel.domain.useCase.template.update

import ru.betel.domain.converters.toUpdateEntity
import ru.betel.domain.dao.TemplateDao
import ru.betel.domain.model.SongTemplate

class UpdateTemplateInLocalUseCase(private val dao: TemplateDao) {
    suspend fun execute(newTemplate: SongTemplate) {
        dao.updateSongTemplate(newTemplate.toUpdateEntity())
    }
}
