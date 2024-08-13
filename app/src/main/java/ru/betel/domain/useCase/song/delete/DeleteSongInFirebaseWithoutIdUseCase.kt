package ru.betel.domain.useCase.song.delete

import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.delete.DeleteSongFromFirebase

class DeleteSongInFirebaseWithoutIdUseCase(private val deleteSongFromFirebase: DeleteSongFromFirebase) {
    suspend fun execute(song: Song, allSongs: List<Song>) =
        deleteSongFromFirebase.deleteSong(song, allSongs)
}