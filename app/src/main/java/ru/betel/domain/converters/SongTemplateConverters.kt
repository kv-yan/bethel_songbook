package ru.betel.domain.converters

import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.entity.SongTemplateEntity

fun SongTemplate.toEntity(): SongTemplateEntity {
    return SongTemplateEntity(
        id = 0,
        this.createDate,
        this.performerName,
        this.weekday,
        this.isSingleMode,
        this.glorifyingSong.toEntity(),
        this.worshipSong.toEntity(),
        this.giftSong.toEntity(),
        this.singleModeSongs.toEntity(),
    )
}

fun SongTemplate.toUpdateEntity(): SongTemplateEntity {
    return SongTemplateEntity(
        id = id.toInt(),
        this.createDate,
        this.performerName,
        this.weekday,
        this.isSingleMode,
        this.glorifyingSong.toEntity(),
        this.worshipSong.toEntity(),
        this.giftSong.toEntity(),
        this.singleModeSongs.toEntity(),
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
                isSingleMode = entity.isSingleMode,
                glorifyingSong = entity.glorifyingSong.toSong(),
                worshipSong = entity.worshipSong.toSong(),
                giftSong = entity.giftSong.toSong(),
                singleModeSongs = entity.singleModeSongs.toSong()
            )
        )
    }
    return songTemplateList
}
