package ru.betel.app.ui.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import ru.betel.app.ui.items.song.SongItemWithDeleteBtn
import ru.betel.data.extensions.toSnapshotStateList
import ru.betel.domain.model.Song

@Composable
fun LazyColumnForAddNewTemplate(
    songList: SnapshotStateList<Song>,
    tonalityLongPres: (Song) -> Unit,
) {
    var data by remember { mutableStateOf(songList) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data = data.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }.toSnapshotStateList()
    }, onDragEnd = { x, y ->
        songList.clear()
        songList.addAll(data)

    })

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 1300.dp)
    ) {
        itemsIndexed(data, key = { index, item -> item.id } ) { index, song ->
            ReorderableItem(state, key = song.id) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp, label = "")

                Box(
                    modifier = Modifier.shadow(elevation.value)
                ) {
                    SongItemWithDeleteBtn(
                        item = song,
                        isLastItem = (data.size-1 == index),
                        index = data.indexOf(song) + 1,
                        onDeleteItemClick = { selectedItem ->
                            data.remove(selectedItem)
                        },
                        onTonalityTempItemLongPres = { tonalityLongPres(song) })
                }
            }
        }
    }
}