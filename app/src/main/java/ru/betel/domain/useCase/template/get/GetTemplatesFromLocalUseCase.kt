package ru.betel.domain.useCase.template.get

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.data.extensions.toSongTemplate
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.get.GetTemplatesFromFirebase
import ru.betel.domain.repository.template.get.GetTemplatesFromLocal

class GetTemplatesFromLocalUseCase(private val getTemplatesFromLocal: GetTemplatesFromLocal) {

    fun execute() = getTemplatesFromLocal.getTemplates()
}