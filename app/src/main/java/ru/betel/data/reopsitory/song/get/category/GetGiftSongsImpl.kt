package ru.betel.data.reopsitory.song.get.category

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.all.GetAllSongs
import ru.betel.domain.repository.song.get.category.GetGiftSongs


class GetGiftSongsImpl(private val getAllSongs: GetAllSongs) : GetGiftSongs {
    private val giftSongsState = MutableStateFlow<MutableList<Song>>(mutableListOf())

    override fun getGiftSongs() = flow {
        val list = mutableListOf<Song>()
        getAllSongs.getAllSongs().collect {
            it.forEach { song ->
                if (song.isGiftSong) list.add(song)
            }
            giftSongsState.value = list
        }
        emit(list)
    }
}
