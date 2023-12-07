package ru.betel.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity


data class SongTemplate constructor(
    var id: String,
    var createDate: String,
    val performerName: String,
    val weekday: String,
    var favorite: Boolean,
    val glorifyingSong: List<Song>,
    val worshipSong: List<Song>,
    val giftSong: List<Song>,
)
