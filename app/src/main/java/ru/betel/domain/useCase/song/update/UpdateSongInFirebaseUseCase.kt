package ru.betel.domain.useCase.song.update

import com.google.firebase.database.FirebaseDatabase
import ru.betel.domain.model.Song

class UpdateSongInFirebaseUseCase {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("Song")

    fun execute(song: Song, updatedSong: Song, allSongList: MutableList<Song>) {
        var songId = ""
        try {
            song.id.toInt()
            for (item in allSongList) {
                if (item.title == song.title && item.words == song.words) {
                    songId = item.id
                    updatedSong.id = item.id
                }
            }

        } catch (ex: NumberFormatException) {
            songId = song.id
        }
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
            "usingSoundTrack" to updatedSong.isUsingSoundTrack,
        )

        songRef.updateChildren(
            updatedValues
        ) { _, _ -> }
    }
}
