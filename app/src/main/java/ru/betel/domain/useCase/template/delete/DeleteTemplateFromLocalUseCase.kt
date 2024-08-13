package ru.betel.domain.useCase.template.delete

import ru.betel.domain.converters.toUpdateEntity
import ru.betel.domain.dao.TemplateDao
import ru.betel.domain.model.SongTemplate

class DeleteTemplateFromLocalUseCase(private val dao: TemplateDao) {
    suspend fun execute(template: SongTemplate) = dao.deleteSongTemplate(template.toUpdateEntity())
}