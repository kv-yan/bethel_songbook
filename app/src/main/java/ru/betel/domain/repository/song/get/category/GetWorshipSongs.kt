package ru.betel.domain.repository.song.get.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song

interface GetWorshipSongs {
//    fun getWorshipSongs(): MutableStateFlow<MutableList<Song>>
    fun getWorshipSongs(): Flow<MutableList<Song>>
}