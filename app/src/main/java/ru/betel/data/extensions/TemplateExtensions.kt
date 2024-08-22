package ru.betel.data.extensions

import org.json.JSONArray
import ru.betel.domain.converters.toSong
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.entity.SongTemplateEntity

fun SongTemplate.getSongsTitle(): String {
    return if (this.isSingleMode) {
        this.singleModeSongs.joinToString(separator = "\n") { it.title }
    } else {
        val glorifyingSongList = this.glorifyingSong.joinToString(separator = "\n") { it.title }
        val worshipSongList = this.worshipSong.joinToString(separator = "\n") { it.title }
        val giftSongList = this.giftSong.joinToString(separator = "\n") { it.title }

        "$glorifyingSongList\n$worshipSongList\n$giftSongList"
    }
}

fun SongTemplate.getMessageForShare(showTitle: Boolean = true): String {
    return if (this.isSingleMode) {
        buildString {
            append(this@getMessageForShare.singleModeSongs.getSongsTitle(showTitle))
        }
    } else {
        buildString {
            append("Փառաբանություն\n")
            append(this@getMessageForShare.glorifyingSong.getSongsTitle(showTitle))
            append("\nԵրկրպագություն\n")
            append(this@getMessageForShare.worshipSong.getSongsTitle(showTitle))
            append("\nԸնծա\n")
            append(this@getMessageForShare.giftSong.getSongsTitle(showTitle))
        }
    }
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

fun SongTemplate.getNotificationBody(): String {
    val text = this.getMessageForShare(false)
    return text
}

private fun parseSongs(jsonArrayString: String?): MutableList<Song> {
    if (jsonArrayString.isNullOrEmpty()) return mutableListOf()

    val jsonArray = JSONArray(jsonArrayString)
    val songs = mutableListOf<Song>()
    for (i in 0 until jsonArray.length()) {
        val songJson = jsonArray.getJSONObject(i)
        val song = Song(
            id = songJson.getString("id"),
            title = songJson.getString("title"),
            tonality = songJson.getString("tonality"),
            words = songJson.getString("words"),
            temp = songJson.getString("temp"),
            isGlorifyingSong = songJson.getBoolean("isGlorifyingSong"),
            isWorshipSong = songJson.getBoolean("isWorshipSong"),
            isGiftSong = songJson.getBoolean("isGiftSong"),
            isFromSongbookSong = songJson.getBoolean("isFromSongbookSong"),
        )
        songs.add(song)
    }
    return songs
}


fun List<Song>.isContainSong(song: Song): Boolean {
    var contains = false

    this.forEach {
        if (it.id == song.id) {
            contains = true
        }
    }
    return contains
}
