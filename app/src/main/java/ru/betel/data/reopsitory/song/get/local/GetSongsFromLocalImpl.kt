package ru.betel.data.reopsitory.song.get.local

import ru.betel.domain.converters.toSong
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.local.GetSongsFromLocal
import ru.betel.domain.dao.SongDao

class GetSongsFromLocalImpl(private val songDao: SongDao) : GetSongsFromLocal {
    override suspend fun getSongs(): MutableList<Song>{
        return songDao.getAllSong().toSong().toMutableList()
    }
}