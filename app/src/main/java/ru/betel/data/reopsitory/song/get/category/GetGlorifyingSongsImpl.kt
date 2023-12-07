package ru.betel.data.reopsitory.song.get.category

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.song.get.all.GetAllSongs
import ru.betel.domain.repository.song.get.category.GetGlorifyingSongs
import ru.betel.domain.useCase.song.GetAllSongsUseCase

class GetGlorifyingSongsImpl(private val getAllSongs: GetAllSongs) :
    GetGlorifyingSongs {
    private val glorifyingSongsState = MutableStateFlow<MutableList<Song>>(mutableListOf())

    override fun getGlorifyingSongs()= flow {
        val list = mutableListOf<Song>()
        getAllSongs.getAllSongs().collect {
            it.forEach { song ->
                if (song.isGlorifyingSong) list.add(song)
            }
            glorifyingSongsState.value = list
        }
        emit(list)
    }
}
