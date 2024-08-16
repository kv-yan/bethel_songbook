package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
    favoriteSongs: List<Song>,
    isEnableLongPress: Boolean = true,
    isForCategory: Boolean = false,
    songbookTextSize: SongbookTextSize,
    onEditClick: (Song) -> Unit,
    onShareClick: (Song) -> Unit,
    onDeleteClick: (Song) -> Unit,
    onFavoriteClick: (Song, Boolean) -> Unit,
    onSongSelected: (Song, Int) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(songs) { index, song ->
                SongItemWithWords(
                    isForCategory = isForCategory,
                    appTheme = appTheme,
                    item = song,
                    favoriteSongs = favoriteSongs,
                    textSize = songbookTextSize,
                    isEnableLongPress = isEnableLongPress,
                    onEditClick = { onEditClick(song) },
                    onFavoriteClick = { song, state -> onFavoriteClick(song, state) },
                    onShareClick = { onShareClick(song) },
                    onDeleteClick = { onDeleteClick(song) }) { onSongSelected(song, index) }
            }
        }
    }
}


