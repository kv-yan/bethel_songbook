package ru.betel.data.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
import ru.betel.domain.converters.toSong
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.entity.SongTemplateEntity

fun SongTemplate.getSongsTitle(): String {
    var songsList = ""
    val glorifyingSong: ArrayList<Song> =
        this.glorifyingSong as ArrayList<Song>
    val worshipSong: ArrayList<Song> =
        this.worshipSong as ArrayList<Song>
    val giftSong: ArrayList<Song> =
        this.giftSong as ArrayList<Song>

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
    giftSong.forEach {
        songsList += "${it.title}\n"
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
                entity.giftSong.toSong()
            )
        )
    }
    return songTemplateList
}