package ru.betel.domain.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.betel.domain.model.entity.SongEntity

@Entity(tableName = "template")
data class SongTemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "create_date") var createDate: String,
    @ColumnInfo(name = "performer_name") val performerName: String,
    @ColumnInfo(name = "weekday") val weekday: String,
    @ColumnInfo(name = "isSingleMode") val isSingleMode: Boolean,
    @ColumnInfo(name = "glorifyingSong") val glorifyingSong: List<SongEntity>,
    @ColumnInfo(name = "worshipSong") val worshipSong: List<SongEntity>,
    @ColumnInfo(name = "giftSong") val giftSong: List<SongEntity>,
    @ColumnInfo(name = "singleModeSongs") val singleModeSongs: List<SongEntity>,
)