package ru.betel.data.reopsitory.song.get.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.all.GetAllSongs
import ru.betel.domain.repository.song.get.category.GetWorshipSongs
import ru.betel.domain.useCase.song.GetAllSongsUseCase


class GetWorshipSongsImpl(private val getAllSongs: GetAllSongs) : GetWorshipSongs {

    private val worshipSongState = MutableStateFlow<MutableList<Song>>(mutableListOf())

    override fun getWorshipSongs(): Flow<MutableList<Song>> = flow {
        val list = mutableListOf<Song>()
        getAllSongs.getAllSongs().collect {
            it.forEach { song ->
                if (song.isWorshipSong) list.add(song)
            }
            worshipSongState.value = list
        }
        emit(list)
    }
}
