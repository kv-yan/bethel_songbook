package ru.betel.data.reopsitory.song.get.category

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.all.GetAllSongs
import ru.betel.domain.repository.song.get.category.GetFromSongbookSongs


class GetFromSongbookSongsImpl(private val getAllSongs: GetAllSongs) :
    GetFromSongbookSongs {
    private val fromSongbookSongsState = MutableStateFlow<MutableList<Song>>(mutableListOf())

    override fun getFromSongbookSongs() = flow {
        val list = mutableListOf<Song>()
        getAllSongs.getAllSongs().collect {
            it.forEach { song ->
                if (song.isFromSongbookSong) list.add(song)
            }
            fromSongbookSongsState.value = list
        }
        emit(list)
    }
}
