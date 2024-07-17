package ru.betel.app.ui.widgets.tabs.teb_screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import ru.betel.app.ui.items.song.SongItemWithWordsAndCheckBox
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AddSong


@Composable
fun TabScreenCategory(
    categorySongs: MutableList<AddSong>,
    onItemLongPress: (Song) -> Unit,
    onItemClick: (AddSong) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(categorySongs) { item ->
            SongItemWithWordsAndCheckBox(item, onItemLongPress = {
                onItemLongPress(it)
            }) {
                onItemClick(item)
            }
        }
    }
}