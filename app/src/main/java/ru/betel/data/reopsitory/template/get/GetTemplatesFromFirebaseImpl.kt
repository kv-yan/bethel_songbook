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
import ru.betel.domain.constants.TEMPLATE_REF
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.get.GetTemplatesFromFirebase

class GetTemplatesFromFirebaseImpl(database: FirebaseDatabase) : GetTemplatesFromFirebase {
    private val databaseRef = database.getReference(TEMPLATE_REF)

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
                    val isSingleMode = template["singleMode"] as Boolean
                    var glorifyingSong: List<Song>
                    var worshipSong: List<Song>
                    var giftSong: List<Song>
                    var singleModeSongs: List<Song>

                    if (isSingleMode){
                        singleModeSongs = getListOfSongs(template["singleModeSongs"] as ArrayList<HashMap<Any, Any>>)
                        glorifyingSong = listOf<Song>()
                        worshipSong = listOf<Song>()
                        giftSong = listOf<Song>()
                    }
                    else {
                        singleModeSongs = listOf<Song>()
                        glorifyingSong = getListOfSongs(template["glorifyingSong"] as ArrayList<HashMap<Any, Any>>)
                        worshipSong = getListOfSongs(template["worshipSong"] as ArrayList<HashMap<Any, Any>>)
                        giftSong = getListOfSongs(template["giftSong"] as ArrayList<HashMap<Any, Any>>)
                    }


                    templateList.add(
                        SongTemplate(
                            id = item.key.toString(),
                            createDate = createDate,
                            performerName = performerName,
                            weekday = weekday,
                            isSingleMode = isSingleMode,
                            glorifyingSong = glorifyingSong,
                            worshipSong = worshipSong,
                            giftSong = giftSong,
                            singleModeSongs = singleModeSongs
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
            val id = item["id"] as String
            val title = item["title"] as String
            val tonality = item["tonality"] as String
            val words = item["words"] as String
            val temp = item["temp"] as String
            val isGlorifyingSong = item["glorifyingSong"] as Boolean
            val isWorshipSong = item["worshipSong"] as Boolean
            val isGiftSong = item["giftSong"] as Boolean
            val isFromSongbookSong = item["fromSongbookSong"] as Boolean
            songList.add(
                Song(
                    id = id,
                    title = title,
                    tonality = tonality,
                    words = words,
                    temp = temp,
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
