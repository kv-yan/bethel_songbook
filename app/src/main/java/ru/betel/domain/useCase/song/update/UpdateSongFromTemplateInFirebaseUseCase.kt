package ru.betel.domain.useCase.song.update

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate

class UpdateSongFromTemplateInFirebaseUseCase {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("SongTemplate")
    private val TAG = "UpdateSongInFirebaseUse"

    fun execute(template: SongTemplate, song: Song, updatedSong: Song) {
        val songListName: String
        val index: Int

        when {
            template.glorifyingSong.contains(song) -> {
                index = template.glorifyingSong.indexOf(song)
                songListName = "glorifyingSong"
            }

            template.worshipSong.contains(song) -> {
                index = template.worshipSong.indexOf(song)
                songListName = "worshipSong"
            }

            template.giftSong.contains(song) -> {
                index = template.giftSong.indexOf(song)
                songListName = "giftSong"
            }

            template.singleModeSongs.contains(song) -> {
                index = template.singleModeSongs.indexOf(song)
                songListName = "singleModeSongs"
            }

            else -> throw IllegalArgumentException("Song not found in any list within the template")
        }

        val templateRef = databaseRef.child("${template.id}/$songListName/$index")


        // Construct the path to the specific song in Firebase
        val songPath = "$songListName/$index"

        Log.d(TAG, "Updating song at path: $songPath")

        // Prepare the map of updated values
        val updatedValues = mapOf(
            "title" to updatedSong.title,
            "tonality" to updatedSong.tonality,
            "words" to updatedSong.words,
            "temp" to updatedSong.temp,
            "glorifyingSong" to updatedSong.isGlorifyingSong,
            "worshipSong" to updatedSong.isWorshipSong,
            "giftSong" to updatedSong.isGiftSong,
            "fromSongbookSong" to updatedSong.isFromSongbookSong,
            "usingSoundTrack" to updatedSong.isUsingSoundTrack
        )


        templateRef
        // Perform the update in Firebase
        templateRef.updateChildren(updatedValues) { databaseError, _ ->
            if (databaseError != null) {
                Log.e(TAG, "Error updating song: ${databaseError.message}")
            } else {
                Log.i(TAG, "Song updated successfully")
            }
        }
    }
}


