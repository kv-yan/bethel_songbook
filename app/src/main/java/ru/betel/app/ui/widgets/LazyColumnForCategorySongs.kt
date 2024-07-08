package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.betel.app.ui.items.song.SongItemWithTonalityAndTemp
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
fun LazyColumnForCategorySongs(
    songList: List<Song>, fontSize: SongbookTextSize, onItemClick: (Song) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 1300.dp)
    ) {
        itemsIndexed(songList) { index, item ->
            SongItemWithTonalityAndTemp(
                item = item,
                fontSize = fontSize,
                index = index + 1,
                isLastItem = (songList.size-1 == index),
            ) {
                onItemClick(it)
            }
        }
    }
}
