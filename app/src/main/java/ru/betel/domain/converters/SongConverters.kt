package ru.betel.domain.converters

import ru.betel.domain.model.Song
import ru.betel.domain.model.entity.SongEntity

fun List<Song>.toEntity(): List<SongEntity> {
    val entityList = mutableListOf<SongEntity>()
    this.forEach {
        entityList.add(
            SongEntity(
                id = 0,
                title = it.title,
                tonality = it.tonality,
                words = it.words,
                isGlorifyingSong = it.isGlorifyingSong,
                isWorshipSong = it.isWorshipSong,
                isGiftSong = it.isGiftSong,
                isFromSongbookSong = it.isFromSongbookSong,
                temp = it.temp
            )

        )
    }
    return entityList
}

fun List<SongEntity>.toSong(): List<Song> {
    val songsList = mutableListOf<Song>()
    this.forEach {
        songsList.add(
            Song(
                id = it.id.toString(),
                title = it.title,
                tonality = it.tonality,
                words = it.words,
                isGlorifyingSong = it.isGlorifyingSong,
                isWorshipSong = it.isWorshipSong,
                isGiftSong = it.isGiftSong,
                isFromSongbookSong = it.isFromSongbookSong,
                temp = it.temp
            )
        )
    }
    return songsList
}

fun Song.toEntity(): SongEntity {
    return SongEntity(
        id = if (this.id == "Song" || this.id is String) 0 else this.id.toInt(),
        title = this.title,
        tonality = this.tonality,
        words = this.words,
        isGlorifyingSong = this.isGlorifyingSong,
        isWorshipSong = this.isWorshipSong,
        isGiftSong = this.isGiftSong,
        isFromSongbookSong = this.isFromSongbookSong,
        temp = this.temp
    )
}
