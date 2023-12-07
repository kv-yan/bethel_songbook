package ru.betel.domain.useCase.song.local

import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.local.GetSongsFromLocal

class GetSongsFromLocalUseCase(private val getSongsFromLocal: GetSongsFromLocal) {
    suspend fun execute(): MutableList<Song> {
        return getSongsFromLocal.getSongs()
    }
}