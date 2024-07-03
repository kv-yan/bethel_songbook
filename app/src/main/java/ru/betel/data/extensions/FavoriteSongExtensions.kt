package ru.betel.data.extensions

import ru.betel.domain.model.Song
import ru.betel.domain.model.entity.FavoriteSongsEntity

fun List<FavoriteSongsEntity>.toSong(): List<Song> {
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

fun Song.toDeleteFavoriteEntity(): FavoriteSongsEntity {

    return FavoriteSongsEntity(
        id = if (this.id == "Song" && this.id is String) 0 else this.id.toInt(),
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

fun Song.toInsertFavoriteEntity(): FavoriteSongsEntity {
    return FavoriteSongsEntity(
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
