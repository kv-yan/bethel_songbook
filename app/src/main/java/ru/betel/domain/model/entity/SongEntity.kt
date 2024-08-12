package ru.betel.domain.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val tonality: String,
    @ColumnInfo val words: String,
    @ColumnInfo val temp: String,
    @ColumnInfo(name = "is_glorifying_song") var isGlorifyingSong: Boolean,
    @ColumnInfo(name = "is_worship_song") var isWorshipSong: Boolean,
    @ColumnInfo(name = "is_gift_song") var isGiftSong: Boolean,
    @ColumnInfo(name = "is_from_songbook_song") var isFromSongbookSong: Boolean,
) {
    constructor() : this("", "Error", "Error", "Error","Error", false, false, false, false)
}