package ru.betel.domain.useCase.song.update

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.model.Song

class UpdateSongInFirebaseUseCase {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("Song")
    private val TAG = "UpdateSongInFirebaseUse"

    fun execute(song: Song, updatedSong: Song, allSongList: MutableList<Song>) {
        var songId: String = ""
        try {
            song.id.toInt()
            val count = allSongList.size
            for (item in allSongList) {
                Log.e(TAG, "execute: ${allSongList.indexOf(item)} / $count")
                Log.e(TAG, "execute: ${item.title} :: ", )
                if (item.title == song.title && item.words == song.words) {
                    songId = item.id
                    updatedSong.id = item.id
                }
            }

        } catch (ex: NumberFormatException) {
            songId = song.id
        }
        val songRef = databaseRef.child(songId)
        Log.e(TAG, "execute: $songId")


        val updatedValues = mapOf(
            "title" to updatedSong.title,
            "tonality" to updatedSong.tonality,
            "words" to updatedSong.words,
            "temp" to updatedSong.temp,
            "glorifyingSong" to updatedSong.isGlorifyingSong,
            "worshipSong" to updatedSong.isWorshipSong,
            "giftSong" to updatedSong.isGiftSong,
            "fromSongbookSong" to updatedSong.isFromSongbookSong,
            "usingSoundTrack" to updatedSong.isUsingSoundTrack,
        )

        songRef.updateChildren(
            updatedValues
        ) { _, _ -> }
    }
}
