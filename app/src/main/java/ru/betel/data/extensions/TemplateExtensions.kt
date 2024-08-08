package ru.betel.data.extensions

import org.json.JSONArray
import ru.betel.domain.converters.toSong
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.entity.SongTemplateEntity

fun SongTemplate.getSongsTitle(): String {
    var songsList = ""
    val glorifyingSong: List<Song> = this.glorifyingSong as List<Song>
    val worshipSong: List<Song> = this.worshipSong as List<Song>
    val giftSong: List<Song> = this.giftSong as List<Song>

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

fun SongTemplate.getMessageForShare(showTitle: Boolean = true): String {
    return "Փառաբանություն\n${
        this.glorifyingSong.getSongsTitle(showTitle)
    }\n Երկրպագություն\n${
        this.worshipSong.getSongsTitle(
            showTitle
        )
    }\n Ընծա\n${
        this.giftSong.getSongsTitle(showTitle)
    }"
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


fun parseTemplateData(data: Map<String, String>): SongTemplate {
    val glorifyingSongs = parseSongs(data["glorifyingSong"])
    val worshipSongs = parseSongs(data["worshipSong"])
    val giftSongs = parseSongs(data["giftSong"])
    val singleModeSongs = parseSongs(data["singleModeSongs"])

    return SongTemplate(
        id = data["id"] ?: "",
        createDate = data["createDate"] ?: "",
        performerName = data["performerName"] ?: "",
        weekday = data["weekday"] ?: "",
        isSingleMode = data["isSingleMode"]?.toBoolean() ?: false,
        glorifyingSong = glorifyingSongs,
        worshipSong = worshipSongs,
        giftSong = giftSongs,
        singleModeSongs = singleModeSongs
    )
}

private fun parseSongs(jsonArrayString: String?): List<Song> {
    if (jsonArrayString.isNullOrEmpty()) return emptyList()

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
