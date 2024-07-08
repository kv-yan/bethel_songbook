package ru.betel.domain.model


data class SongTemplate(
    var id: String,
    var createDate: String,
    val performerName: String,
    val weekday: String,
    var favorite: Boolean,
    val glorifyingSong: List<Song>,
    val worshipSong: List<Song>,
    val giftSong: List<Song>,
)
