package ru.betel.domain.repository.song.get.firebase

import androidx.compose.runtime.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.betel.domain.model.Song

interface GetSongsFromFirebase {
     suspend fun getAllSongs(): List<Song>
}