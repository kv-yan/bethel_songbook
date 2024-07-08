package ru.betel.data.extensions

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AddSong

fun Song.getWordsFirst2Lines(): String {
    val lines = this.words.split("\n")
    val firstTwoLines = lines.take(2)
    return firstTwoLines.joinToString("\n")
}

fun Song.getMessageForShare(): String {
    return "${this.title} \n\n ${this.words}"
}

fun List<Song>.getSongsTitle(): String {
    var songList = ""
    this.forEach {
        songList += "  ${it.title} (${it.tonality})\n"
    }
    return songList
}

fun List<AddSong>.updateSong(addSong: AddSong) {
    this.forEach {
        if (it.song == addSong.song) {
            it.isAdded.value = addSong.isAdded.value
        }
    }
}

fun MutableList<Song>.toMutableAddSongList(): MutableList<AddSong> {
    val addSongList = mutableListOf<AddSong>()
    this.forEach {
        addSongList.add(AddSong(it, mutableStateOf(false)))
    }
    return addSongList
}

fun List<Song>.toImmutableAddSongList(): MutableList<AddSong> {
    val addSongList = mutableListOf<AddSong>()
    this.forEach {
        addSongList.add(AddSong(it, mutableStateOf(false)))
    }
    return addSongList
}

fun SnapshotStateList<AddSong>.toSongList(): MutableList<Song> {
    val songList = mutableListOf<Song>()
    this.forEach {
        songList.add(it.song)
    }
    return songList
}

fun MutableList<Song>.toSnapshotStateList(): SnapshotStateList<Song> {
    val list = mutableStateListOf<Song>()
    this.forEach {
        list.add(it)
    }
    println("worked")

    return list
}