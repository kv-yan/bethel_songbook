package ru.betel.app.ui.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
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
    songList: SnapshotStateList<Song>, onSelectedTonalityLongPres: (Song) -> Unit,
) {
    var data = remember { songList }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data = data.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }.toSnapshotStateList()
    })

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 1300.dp)

    ) {
        itemsIndexed(data, key = { _, item -> item.title }) { index, item ->
            ReorderableItem(state, key = index) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp, label = "")

                Box(
                    modifier = Modifier.shadow(elevation.value)
                ) {
                    SongItemWithDeleteBtn(item, index + 1, onDeleteItemClick = { item: Song ->
                        data.remove(item)
                    }) { onSelectedTonalityLongPres(item) }
                }
            }
        }
    }
}


@Preview
@Composable
fun VerticalReorderList() {
    val data = remember { mutableStateOf(List(100) { "Item $it" }) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data.value, { it }) { item ->
            ReorderableItem(state, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp, label = "")
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(MaterialTheme.colors.surface)
                ) {
                    Text(item)
                }
            }
        }
    }
}
