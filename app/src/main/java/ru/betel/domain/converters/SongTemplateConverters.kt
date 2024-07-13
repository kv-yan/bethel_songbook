package ru.betel.domain.converters

import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.entity.SongTemplateEntity
import java.util.*

fun SongTemplate.toEntity(): SongTemplateEntity {
    return SongTemplateEntity(
        id = this.id.toInt(),
        this.createDate,
        this.performerName,
        this.weekday,
        this.isSingleMode,
        this.glorifyingSong.toEntity(),
        this.worshipSong.toEntity(),
        this.giftSong.toEntity(),
    )
}

fun List<SongTemplateEntity>.toSongTemplate(): MutableList<SongTemplate> {
    val songTemplateList = mutableListOf<SongTemplate>()
    this.forEach { entity ->
        songTemplateList.add(
            SongTemplate(
                id = entity.id.toString(),
                createDate = entity.createDate,
                performerName = entity.performerName,
                weekday = entity.weekday,
                isSingleMode = entity.favorite,
                glorifyingSong = entity.glorifyingSong.toSong(),
                worshipSong = entity.worshipSong.toSong(),
                giftSong = entity.giftSong.toSong(),
                singleModeSongs = emptyList()
            )
        )
    }
    return songTemplateList
}
