package ru.betel.domain.useCase.song.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.category.GetWorshipSongs
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase

class GetWorshipSongsUseCase(private val getWorshipSongs: GetWorshipSongs) {
    fun execute(): Flow<MutableList<Song>> = getWorshipSongs.getWorshipSongs()
}