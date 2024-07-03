package ru.betel.data.reopsitory.template.get

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.get.GetTemplatesFromFirebase

class GetTemplatesFromFirebaseImpl(database: FirebaseDatabase) : GetTemplatesFromFirebase {
    private val databaseRef = database.getReference("SongsTemplate")

    override fun getTemplates(): Flow<List<SongTemplate>> = callbackFlow {
        val templateList = mutableListOf<SongTemplate>()
        val vListener = object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(p0: DataSnapshot) {
                if (templateList.isNotEmpty()) templateList.clear()
                for (item: DataSnapshot in p0.children) {
                    val template: HashMap<String, Any> = item.value as HashMap<String, Any>
                    val createDate = template["createDate"] as String
                    val performerName = template["performerName"] as String
                    val weekday = template["weekday"] as String
                    val favorite = template["favorite"] as Boolean
                    val glorifyingSong =
                        getListOfSongs(template["glorifyingSong"] as ArrayList<HashMap<Any, Any>>)
                    val worshipSong =
                        getListOfSongs(template["worshipSong"] as ArrayList<HashMap<Any, Any>>)
                    val giftSong =
                        getListOfSongs(template["giftSong"] as ArrayList<HashMap<Any, Any>>)
                    templateList.add(
                        SongTemplate(
                            item.key.toString(),
                            createDate,
                            performerName,
                            weekday,
                            favorite,
                            glorifyingSong,
                            worshipSong,
                            giftSong
                        )
                    )
                }
                trySend(templateList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        databaseRef.addValueEventListener(vListener)
        awaitClose { databaseRef.removeEventListener(vListener) }
    }

    private fun getListOfSongs(song: ArrayList<HashMap<Any, Any>>): List<Song> {
        val songList = mutableListOf<Song>()
        for (item in song) {
            val title = item["title"] as String
            val tonality = item["tonality"] as String
            val words = item["words"] as String
            val temp = try {
                item["temp"] as Long
            } catch (e: Exception) {
                item["temp"] as String
            }
            val isGlorifyingSong = item["glorifyingSong"] as Boolean
            val isWorshipSong = item["worshipSong"] as Boolean
            val isGiftSong = item["giftSong"] as Boolean
            val isFromSongbookSong = item["fromSongbookSong"] as Boolean
            songList.add(
                Song(
                    id = databaseRef.key.orEmpty(),
                    title = title,
                    tonality = tonality,
                    words = words,
                    temp = temp.toString().toInt().toString(),
                    isGlorifyingSong = isGlorifyingSong,
                    isWorshipSong = isWorshipSong,
                    isGiftSong = isGiftSong,
                    isFromSongbookSong = isFromSongbookSong
                )
            )
        }
        return songList
    }
}
