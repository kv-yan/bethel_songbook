package ru.betel.data.extensions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import ru.betel.domain.model.ui.AddSong

fun State<MutableList<AddSong>>.toMutableState(): MutableState<MutableList<AddSong>> {
    val value = this.value
    return mutableStateOf(value)
}

fun MutableList<AddSong>.toMutableState(): MutableState<MutableList<AddSong>> {
    val value = this
    return mutableStateOf(value)
}
