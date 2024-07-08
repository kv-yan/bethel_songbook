package ru.betel.data.reopsitory.song.delete

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.delete.DeleteSongFromFirebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DeleteSongFromFirebaseImpl(database: FirebaseDatabase) : DeleteSongFromFirebase {
    private val databaseRef = database.getReference("Song")
    override suspend fun deleteSong(song: Song): Boolean = suspendCancellableCoroutine { continuation ->
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
}