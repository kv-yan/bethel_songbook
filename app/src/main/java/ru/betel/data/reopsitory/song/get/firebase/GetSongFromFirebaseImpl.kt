package ru.betel.data.reopsitory.song.get.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GetSongFromFirebaseImpl(database: FirebaseDatabase) : GetSongsFromFirebase {
    private val TAG = "GetSongFromFirebaseImpl"
    private val databaseRef = database.getReference("Song")
    override suspend fun getAllSongs(): List<Song> = suspendCancellableCoroutine { continuation ->
        val allSongList = mutableListOf<Song>()
        val vListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (allSongList.size > 0) allSongList.clear()
                for (item: DataSnapshot in p0.children) {

                    val song: HashMap<Any, Any> = item.value as HashMap<Any, Any>
                    val isGlorifyingSong = song.getValue("glorifyingSong") as Boolean
                    val isWorshipSong = song.getValue("worshipSong") as Boolean
                    val isGiftSong = song.getValue("giftSong") as Boolean
                    val isFromSongbookSong = song.getValue("fromSongbookSong") as Boolean

                    val title = song.getValue("title") as String
                    val tonality = song.getValue("tonality") as String
                    val words = song.getValue("words") as String
                    val temp = try {
                        song.getValue("temp") as Long
                    } catch (e :Exception ){
                        song.getValue("temp") as String
                    }
                    val id = item.key as String

                    val songObj = Song(
                        id = id,
                        title = title,
                        tonality = tonality,
                        words = words,
                        temp = temp.toString().toInt().toString(),
                        isGlorifyingSong = isGlorifyingSong,
                        isWorshipSong = isWorshipSong,
                        isGiftSong = isGiftSong,
                        isFromSongbookSong = isFromSongbookSong
                    )

                    allSongList.add(songObj)
                }

                continuation.resume(allSongList)
            }

            override fun onCancelled(p0: DatabaseError) {
                continuation.resumeWithException(p0.toException())
            }
        }

        databaseRef.addListenerForSingleValueEvent(vListener)

        // Register a cancellation listener
        continuation.invokeOnCancellation {
            databaseRef.removeEventListener(vListener)
        }
    }
}
