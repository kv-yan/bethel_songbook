package ru.betel.domain.useCase.song.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.category.GetFromSongbookSongs
import ru.betel.domain.repository.song.get.category.GetGiftSongs
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase

class GetGiftSongsUseCase(private val getGiftSongs: GetGiftSongs) {
    fun execute(): Flow<MutableList<Song>> = getGiftSongs.getGiftSongs()
}

