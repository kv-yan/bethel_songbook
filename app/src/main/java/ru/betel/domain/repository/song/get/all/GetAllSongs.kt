package ru.betel.domain.repository.song.get.all

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song

interface GetAllSongs {
    fun getAllSongs(): Flow<List<Song>>
}