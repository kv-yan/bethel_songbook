package ru.betel.data.reopsitory.template.delete

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.betel.domain.constants.TEMPLATE_REF
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.song.delete.DeleteSongFromFirebase
import ru.betel.domain.repository.template.delete.DeleteTemplateFromFirebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DeleteTemplateFromFirebaseImpl(database: FirebaseDatabase) : DeleteTemplateFromFirebase {
    private val databaseRef = database.getReference(TEMPLATE_REF)
    override suspend fun deleteSong(song: SongTemplate): Boolean = suspendCancellableCoroutine { continuation ->
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