package ru.betel.data.reopsitory.sync.song

import ru.betel.domain.converters.toEntity
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase
import ru.betel.domain.dao.SongDao
import ru.betel.domain.repository.song.sync.SyncSongsFromFBToLocalStorage

/*
class SyncSongsFromFBToLocalStorageImpl(
    private val getSongsFromFirebase: GetSongsFromFirebase, private val songDao: SongDao
) : SyncSongsFromFBToLocalStorage {

    override suspend fun syncData() {
        println("localSongs :: ${songDao.getAllSong().size}")
        println("getSongsFromFirebase :: ${getSongsFromFirebase.getSongs().value.size}")

        getSongsFromFirebase.getSongs().value.forEach { fbSong ->
            if (songDao.getAllSong().isEmpty()) {
                songDao.insertSong(fbSong.toEntity())
                return@forEach
            } else {
                songDao.getAllSong().forEach { localSong ->
                    if (fbSong.title != localSong.title) {
                        songDao.insertSong(fbSong.toEntity())
                    } else {
                        if (fbSong.words != localSong.words) {
                            songDao.insertSong(fbSong.toEntity())
                        }
                    }
                }
            }
        }
        println("localSongs :: ${songDao.getAllSong().size}")
    }
}*/
class SyncSongsFromFBToLocalStorageImpl(
    private val getSongsFromFirebase: GetSongsFromFirebase,
    private val songDao: SongDao,
) : SyncSongsFromFBToLocalStorage {

    override suspend fun syncData() {
        // Step 1: Clear local database
        songDao.cleareAllSongs()

        // Step 2: Fetch songs from Firebase and insert into local database
        val fbSongs: List<Song> = getSongsFromFirebase.getAllSongs()
        val songEntities = fbSongs.map { it.toEntity() }
        songDao.insertSongs(songEntities)
    }
}
