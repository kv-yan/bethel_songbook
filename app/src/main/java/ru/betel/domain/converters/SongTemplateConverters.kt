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
        this.favorite,
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
