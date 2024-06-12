package ru.betel.domain.useCase.song.set

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.model.Song

class SaveSongInFirebaseUseCase {
    private val TAG = "SaveSongInFirebaseUseCa"
    fun execute(song: Song): Boolean {
        return try {
            FirebaseDatabase.getInstance().getReference("Song").push().setValue(song)
            true
        } catch (ex: Exception) {
            Log.e(TAG, "execute: ex :: ${ex.message}")
            false
        }
    }
}