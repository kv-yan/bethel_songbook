package ru.betel.domain.useCase.song.delete

import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.delete.DeleteSongFromFirebase

class DeleteSongFromFirebaseUseCase(private val deleteSongFromFirebase: DeleteSongFromFirebase) {
    suspend fun execute(song: Song) = deleteSongFromFirebase.deleteSong(song)
}