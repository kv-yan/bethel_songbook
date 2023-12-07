package ru.betel.domain.model

data class SongCategory(
    val charName: String,
    val items: List<Song>,
)