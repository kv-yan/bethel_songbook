package ru.betel.data.reopsitory.template.get

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.get.GetTemplatesFromFirebase
import java.util.NoSuchElementException

class GetTemplatesFromFirebaseImpl(database: FirebaseDatabase) : GetTemplatesFromFirebase {
    private val databaseRef = database.getReference("SongsTemplate")

    override fun getTemplates(): MutableStateFlow<MutableList<SongTemplate>> {
        var templateList = mutableListOf<SongTemplate>()
        lateinit var createDate: String
        lateinit var performerName: String
        lateinit var weekday: String
        var favorite: Boolean
        var glorifyingSong: List<Song>
        var worshipSong: List<Song>
        var giftSong: List<Song>
        try {
            val vListener: ValueEventListener = object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(p0: DataSnapshot) {
                    if (templateList.size > 0) templateList.clear()
                    for (item: DataSnapshot in p0.children) {
                        val template: HashMap<String, Any> =
                            item.value as HashMap<String, Any>
                        createDate = template.getValue("createDate") as String
                        performerName = template.getValue("performerName") as String
                        weekday = template.getValue("weekday") as String
                        favorite = template.getValue("favorite") as Boolean
                        glorifyingSong = getListOfSongs(template.getValue("glorifyingSong") as ArrayList<HashMap<Any, Any>>)
                        worshipSong =
                            getListOfSongs(template.getValue("worshipSong") as ArrayList<HashMap<Any, Any>>)
                        giftSong =
                            getListOfSongs(template.getValue("giftSong") as ArrayList<HashMap<Any, Any>>)
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
                }

                override fun onCancelled(p0: DatabaseError) {
                    // do nothing
                }
            }
            databaseRef.addValueEventListener(vListener)
        } catch (ex: NoSuchElementException) {
            templateList = mutableListOf()

        }
        return MutableStateFlow(templateList)
    }

    private fun getListOfSongs(song: ArrayList<HashMap<Any, Any>>): List<Song> {
        val songList = mutableListOf<Song>()

        for (item in song) {
            lateinit var title: String
            lateinit var tonality: String
            lateinit var words: String
            var isGlorifyingSong: Boolean
            var isWorshipSong: Boolean
            var isGiftSong: Boolean
            var isFromSongbookSong: Boolean
            val songItem: HashMap<Any, Any> = item
            title = songItem.getValue("title") as String
            tonality = songItem.getValue("tonality") as String
            words = songItem.getValue("words") as String
            isGlorifyingSong = songItem.getValue("glorifyingSong") as Boolean
            isWorshipSong = songItem.getValue("worshipSong") as Boolean
            isGiftSong = songItem.getValue("giftSong") as Boolean
            isFromSongbookSong = songItem.getValue("fromSongbookSong") as Boolean
            databaseRef.key?.let {
                Song(
                    it, title, tonality, words, isGlorifyingSong,
                    isWorshipSong, isGiftSong, isFromSongbookSong
                )
            }?.let {
                songList.add(it)
            }
        }
        return songList
    }
}