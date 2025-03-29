package ru.betel.data.reopsitory.sync.song

import ru.betel.domain.converters.toEntity
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase
import ru.betel.domain.dao.SongDao
import ru.betel.domain.repository.song.sync.SyncSongsFromFBToLocalStorage

class SyncSongsFromFBToLocalStorageImpl(
    private val getSongsFromFirebase: GetSongsFromFirebase,
    private val songDao: SongDao,
) : SyncSongsFromFBToLocalStorage {

    override suspend fun syncData() {
        songDao.cleareAllSongs()

        val fbSongs: List<Song> = getSongsFromFirebase.getAllSongs()
        val songEntities = fbSongs.map { it.toEntity() }
        songDao.insertSongs(songEntities)
    }
}
