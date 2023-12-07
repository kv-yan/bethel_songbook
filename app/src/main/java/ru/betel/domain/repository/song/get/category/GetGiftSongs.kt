package ru.betel.domain.repository.song.get.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song

interface GetGiftSongs {
    fun getGiftSongs(): Flow<MutableList<Song>>
}