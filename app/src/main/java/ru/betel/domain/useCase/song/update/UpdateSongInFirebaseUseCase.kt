package ru.betel.domain.useCase.song.update

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.model.Song

class UpdateSongInFirebaseUseCase {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("Song")
    private val TAG = "UpdateSongInFirebaseUse"

    fun execute(song: Song, updatedSong: Song) {
        val songId = song.id
        val songRef = databaseRef.child(songId)


        val updatedValues = mapOf(
            "title" to updatedSong.title,
            "tonality" to updatedSong.tonality,
            "words" to updatedSong.words,
            "temp" to updatedSong.temp,
            "glorifyingSong" to updatedSong.isGlorifyingSong,
            "worshipSong" to updatedSong.isWorshipSong,
            "giftSong" to updatedSong.isGiftSong,
            "fromSongbookSong" to updatedSong.isFromSongbookSong,
        )

        Log.e(TAG, "execute: updatedSong.isGlorifyingSong ։ ${updatedSong.isGlorifyingSong}")
        Log.e(TAG, "execute: updatedSong.isWorshipSong ։ ${updatedSong.isWorshipSong}")
        Log.e(TAG, "execute: updatedSong.isGiftSong ։ ${updatedSong.isGiftSong}")
        Log.e(TAG, "execute: updatedSong.isFromSongbookSong ։ ${updatedSong.isFromSongbookSong}")

        songRef.updateChildren(
            updatedValues
        ) { _, _ -> }
    }
}
