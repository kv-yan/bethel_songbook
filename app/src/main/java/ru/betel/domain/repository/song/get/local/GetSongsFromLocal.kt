package ru.betel.domain.repository.song.get.local

import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song

interface GetSongsFromLocal {
    suspend fun getSongs(): MutableList<Song>
}