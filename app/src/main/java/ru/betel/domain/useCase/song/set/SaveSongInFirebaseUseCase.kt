package ru.betel.domain.useCase.song.set

import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.model.Song

class SaveSongInFirebaseUseCase {
    fun execute(song: Song): Boolean {
        return try {
            FirebaseDatabase.getInstance().getReference("Song").push().setValue(song)
            true
        } catch (ex: Exception) {
            false
        }
    }
}