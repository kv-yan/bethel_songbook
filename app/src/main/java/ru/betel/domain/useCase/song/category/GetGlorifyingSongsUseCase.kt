package ru.betel.domain.useCase.song.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.category.GetGlorifyingSongs
import ru.betel.domain.repository.song.get.category.GetWorshipSongs

class GetGlorifyingSongsUseCase(private val getGlorifyingSong: GetGlorifyingSongs) {
    fun execute(): Flow<MutableList<Song>> = getGlorifyingSong.getGlorifyingSongs()
}