package ru.betel.domain.useCase.template.set

import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.set.local.SaveTemplateToLocal

class SaveTemplateToLocalUseCase(private val saveTemplateToLocal: SaveTemplateToLocal) {
    suspend fun execute(songTemplate: SongTemplate) = saveTemplateToLocal.saveTemplate(songTemplate)
}