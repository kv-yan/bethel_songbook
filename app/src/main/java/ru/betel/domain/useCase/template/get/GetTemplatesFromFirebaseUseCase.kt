package ru.betel.domain.useCase.template.get

import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.get.GetTemplatesFromFirebase

class GetTemplatesFromFirebaseUseCase (private val getSongFromFirebase: GetTemplatesFromFirebase) {
     fun execute() = getSongFromFirebase.getTemplates()
}