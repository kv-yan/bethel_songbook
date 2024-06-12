package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.betel.app.ui.items.song.SongItemWithWords
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
fun SongsList(
    songs: List<Song>,
    songbookTextSize: SongbookTextSize,
    onEditClick: (Song) -> Unit,
    onShareClick: (Song) -> Unit,
    onDeleteClick: (Song) -> Unit,
    onSongSelected: (Song) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(songs) { song ->
                SongItemWithWords(song,
                    songbookTextSize,
                    onEditClick = { onEditClick(song) },
                    onShareClick = { onEditClick(song) },
                    onDeleteClick = { onDeleteClick(song) }) { onSongSelected(song) }
            }
        }
    }
}
