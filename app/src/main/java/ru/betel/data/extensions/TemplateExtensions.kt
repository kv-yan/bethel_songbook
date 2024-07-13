package ru.betel.data.extensions

import ru.betel.domain.converters.toSong
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.entity.SongTemplateEntity

fun SongTemplate.getSongsTitle(): String {
    var songsList = ""
    val glorifyingSong: List<Song> =
        this.glorifyingSong as List<Song>
    val worshipSong: List<Song> =
        this.worshipSong as List<Song>
    val giftSong: List<Song> =
        this.giftSong as List<Song>

    glorifyingSong.forEach {
        songsList += "${it.title}\n"
    }
    val glorifyingSongList = songsList
    songsList = ""
    worshipSong.forEach {
        songsList += "${it.title}\n"
    }
    val worshipSongList = songsList
    songsList = ""
    giftSong.forEachIndexed { index, it ->
        songsList += "${it.title}${if (giftSong.size - 1 == index) "" else "\n"}"
    }
    val giftSongList = songsList
    return "$glorifyingSongList\n$worshipSongList\n$giftSongList"
}

fun SongTemplate.getMessageForShare(): String {
    return "Փառաբանություն\n${this.glorifyingSong.getSongsTitle()}\n Երկրպագություն\n${this.worshipSong.getSongsTitle()}\n Ընծա\n${this.giftSong.getSongsTitle()}"
}


fun List<SongTemplateEntity>.toSongTemplate(): MutableList<SongTemplate> {
    val songTemplateList = mutableListOf<SongTemplate>()
    this.forEach { entity ->
        songTemplateList.add(
            SongTemplate(
                entity.id.toString(),
                entity.createDate,
                entity.performerName,
                entity.weekday,
                entity.favorite,
                entity.glorifyingSong.toSong(),
                entity.worshipSong.toSong(),
                entity.giftSong.toSong(),
                singleModeSongs = emptyList()
            )
        )
    }
    return songTemplateList
}