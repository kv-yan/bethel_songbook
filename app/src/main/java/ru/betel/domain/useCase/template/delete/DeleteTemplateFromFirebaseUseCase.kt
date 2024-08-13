package ru.betel.domain.useCase.template.delete

import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.delete.DeleteTemplateFromFirebase

class DeleteTemplateFromFirebaseUseCase(private val deleteTemplateFromFirebase: DeleteTemplateFromFirebase) {
    suspend fun execute(song: SongTemplate) = deleteTemplateFromFirebase.deleteSong(song)
}