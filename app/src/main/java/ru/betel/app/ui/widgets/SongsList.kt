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
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
fun SongsList(
    appTheme: AppTheme,
    songs: List<Song>,
    isEnableLongPress: Boolean = true,
    songbookTextSize: SongbookTextSize,
    onEditClick: (Song) -> Unit,
    onShareClick: (Song) -> Unit,
    onDeleteClick: (Song) -> Unit,
    onFavoriteClick: (Song, Boolean) -> Unit,
    onSongSelected: (Song) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(songs) { song ->
                SongItemWithWords(
                    isEnableLongPress = isEnableLongPress,
                    appTheme = appTheme,
                    item = song,
                    favoriteSongs = emptyList(),
                    textSize = songbookTextSize,
                    onEditClick = { onEditClick(song) },
                    onFavoriteClick = { _, _ -> },
                    onShareClick = { onShareClick(song) },
                    onDeleteClick = { onDeleteClick(song) }) { onSongSelected(song) }
            }
        }
    }
}
