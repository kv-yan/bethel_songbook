package ru.betel.domain.useCase.song

import ru.betel.domain.repository.song.get.all.GetAllSongs

class GetAllSongsUseCase(private val getAllSongs: GetAllSongs) {
    fun execute() = getAllSongs.getAllSongs()
}
