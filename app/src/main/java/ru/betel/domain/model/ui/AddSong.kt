package ru.betel.domain.model.ui

import androidx.compose.runtime.MutableState
import ru.betel.domain.model.Song

data class AddSong(val song: Song, val isAdded: MutableState<Boolean>)