package ru.betel.data.reopsitory.song.delete

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.betel.domain.dao.FavoriteSongsDao
import ru.betel.domain.dao.SongDao
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.delete.DeleteSongFromFirebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DeleteSongFromFirebaseImpl(
    database: FirebaseDatabase,
    private val favoriteSongsDao: FavoriteSongsDao,
    private val songDao: SongDao,
) : DeleteSongFromFirebase {
    private val databaseRef = database.getReference("Song")
    override suspend fun deleteSong(song: Song): Boolean =
        suspendCancellableCoroutine { continuation ->
            val songRef = databaseRef.child(song.id)

            songRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }

    override suspend fun deleteSong(song: Song, allSongs: List<Song>): Boolean =
        suspendCancellableCoroutine { continuation ->
            var songId = song.id

            try {
                song.id.toInt()

                allSongs.forEach {
                    if (song.title == it.title && song.words == it.words) {
                        songId = it.id
                    }
                }

            } catch (exception: NumberFormatException) {
                songId = song.id
            }

            val songRef = databaseRef.child(songId)

            songRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
}