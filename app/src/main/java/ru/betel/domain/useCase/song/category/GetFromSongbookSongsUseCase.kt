package ru.betel.domain.useCase.song.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.category.GetFromSongbookSongs
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase

class GetFromSongbookSongsUseCase(private val getFromSongbookSongs: GetFromSongbookSongs) {
    fun execute(): Flow<MutableList<Song>> = getFromSongbookSongs.getFromSongbookSongs()
}
