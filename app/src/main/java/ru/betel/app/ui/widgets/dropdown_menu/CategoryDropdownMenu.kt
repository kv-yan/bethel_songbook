package ru.betel.app.ui.widgets.dropdown_menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import ru.betel.app.ui.items.category.CategoryMenuItem
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.ui.SongsCategory

/*
@Composable
fun CategoryDropdownMenu(expanded: MutableState<Boolean>, selectedCategoryItem: StateFlow<SongsCategory>) {
    val category = remember {
        mutableStateOf(
            listOf(
                SongsCategory.ALL,
                SongsCategory.GLORIFYING,
                SongsCategory.WORSHIP,
                SongsCategory.GIFT,
                SongsCategory.FROM_SONGBOOK,
            )
        )
    }


    Box {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(x = 0.dp, y = 12.dp)
        ) {
            CategoryMenuItem(category = category.value[0], isShowDropdownMenu = expanded) { item ->
                category.value.forEach {
                    it.isSelected = item.title == it.title
                }
                category.value = category.value
            }

            CategoryMenuItem(category = category.value[1], isShowDropdownMenu = expanded) { item ->
                category.value.forEach {
                    it.isSelected = item.title == it.title
                }
                category.value = category.value
            }

            CategoryMenuItem(category = category.value[2], isShowDropdownMenu = expanded) { item ->
                category.value.forEach {
                    it.isSelected = item.title == it.title
                }
                category.value = category.value
            }

            CategoryMenuItem(category = category.value[3], isShowDropdownMenu = expanded) { item ->
                category.value.forEach {
                    it.isSelected = item.title == it.title
                }
                category.value = category.value
            }
        }
    }
}
*/


@Composable
fun CategoryDropdownMenu(viewModel: SongViewModel) {
    val categories = remember {
        listOf(
            SongsCategory.ALL,
            SongsCategory.GLORIFYING,
            SongsCategory.WORSHIP,
            SongsCategory.GIFT,
            SongsCategory.FROM_SONGBOOK,
        )
    }
    DropdownMenu(
        expanded = viewModel.isDropdownMenuVisible.value, onDismissRequest = {
            viewModel.isDropdownMenuVisible.value = false
        }, offset = DpOffset(x = 0.dp, y = 12.dp)
    ) {
        categories.forEach { category ->
            CategoryMenuItem(isShowDropdownMenu = viewModel.isDropdownMenuVisible,
                category = category,
                onClick = {
                    viewModel.onCategorySelected(it)
                    categories.forEach { songsCategory ->
                        songsCategory.isSelected = songsCategory == viewModel.selectedCategory.value
                    }
                }

            )
        }
    }
}
