package ru.betel.app.ui.widgets.tabs

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.widgets.pop_up.SeeSongDialog
import ru.betel.app.ui.widgets.tabs.teb_screens.TabScreenCategory
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AddSong

private val TAG = "single_mode"

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoryTabs(
    categorySongs: MutableList<AddSong>,
    categoryTitle: String,
    isSingleMode: MutableState<Boolean>,
    allSongs: State<MutableList<AddSong>>,
    favoriteSongs: State<MutableList<AddSong>>,
    searchAppBarText: MutableState<String>,
    categoryListForAdd: SnapshotStateList<Song>,
    handleBackPress: () -> Unit,
) {

    updateIsAddedState(categorySongs, categoryListForAdd)
    updateIsAddedState(allSongs.value, categoryListForAdd)
    updateIsAddedState(favoriteSongs.value, categoryListForAdd)


    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabRowItems = if (isSingleMode.value) {
        listOf("Առանձնացվածները", "Բոլորը")
    } else {
        listOf(categoryTitle, "Առանձնացվածները", "Բոլորը")
    }
    val horizontalScrollState = rememberScrollState()
    val selectedSongForShowing = remember {
        mutableStateOf(
            Song(
                id = "ERROR",
                title = "ERROR",
                tonality = "ERROR",
                words = "ERROR",
                isGlorifyingSong = true,
                isWorshipSong = false,
                isGiftSong = true,
                isFromSongbookSong = false,
                temp = "Error"
            )
        )

    }
    val isShowViewSongDialog = remember { mutableStateOf(false) }


    BackHandler {
        handleBackPress()
    }

    /****************** songs for showing in tabs ******************/

    val sortedCategorySong: State<List<AddSong>> = derivedStateOf {
        val songs = categorySongs
        if (searchAppBarText.value.isNotBlank()) {
            songs.filter { song ->
                song.song.title.lowercase()
                    .contains(searchAppBarText.value.lowercase(), ignoreCase = true)
            }.sortedBy { song ->
                song.song.title.lowercase()
                song.song.words.lowercase()
            }
        } else {
            songs
        }
    }
    val sortedAllSong: State<List<AddSong>> = derivedStateOf {
        if (searchAppBarText.value.isNotBlank()) {
            allSongs.value.filter { song ->

                song.song.title.lowercase()
                    .contains(searchAppBarText.value.lowercase(), ignoreCase = true)
            }.sortedBy { song ->
                song.song.title.lowercase()
                song.song.words.lowercase()
            }
        } else {
            allSongs.value
        }
    }
    val sortedFavoriteSong: State<List<AddSong>> = derivedStateOf {
        if (searchAppBarText.value.isNotBlank()) {
            favoriteSongs.value.filter { item ->
                item.song.title.lowercase()
                    .contains(searchAppBarText.value.lowercase(), ignoreCase = true)
            }.sortedBy { item ->
                item.song.title.lowercase()
                item.song.words.lowercase()
            }
        } else {
            favoriteSongs.value
        }
    }

    val pagerSongsList = if (isSingleMode.value) listOf(sortedFavoriteSong, sortedAllSong)
    else listOf(sortedCategorySong, sortedFavoriteSong, sortedAllSong)

    Column(
        modifier = Modifier
    ) {
        TabRow(
            modifier = Modifier
                .horizontalScroll(horizontalScrollState)
                .widthIn(min = 100.dp, max = if (!isSingleMode.value) 480.dp else 350.dp),
            backgroundColor = Color.White,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = actionBarColor
                )
            },
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            text = item,
                        )
                    })
            }
        }

        HorizontalPager(
            count = tabRowItems.size, state = pagerState, modifier = Modifier.fillMaxSize()
        ) {
            when (it) {
                0 -> {
                    TabScreenCategory(categorySongs = pagerSongsList[it].value.toMutableList(),
                        onItemClick = { item ->
                            if (!item.isAdded.value) {
                                categoryListForAdd.add(item.song)
                                item.isAdded.value = !item.isAdded.value
                            } else {
                                categoryListForAdd.remove(item.song)
                                item.isAdded.value = !item.isAdded.value
                            }
                            updateIsAddedState(
                                sortedFavoriteSong.value.toMutableList(), categoryListForAdd
                            )
                            updateIsAddedState(
                                sortedAllSong.value.toMutableList(), categoryListForAdd
                            )
                        },
                        onItemLongPress = { songItem ->
                            selectedSongForShowing.value = songItem
                            isShowViewSongDialog.value = true
                        })
                }

                1 -> {
                    TabScreenCategory(categorySongs = pagerSongsList[it].value.toMutableList(),
                        onItemClick = { item ->
                            if (!item.isAdded.value) {
                                categoryListForAdd.add(item.song)
                                item.isAdded.value = !item.isAdded.value
                            } else {
                                categoryListForAdd.remove(item.song)
                                item.isAdded.value = !item.isAdded.value
                            }
                            updateIsAddedState(
                                sortedAllSong.value.toMutableList(), categoryListForAdd
                            )
                            updateIsAddedState(
                                sortedCategorySong.value.toMutableList(), categoryListForAdd
                            )
                        },
                        onItemLongPress = { songItem ->
                            selectedSongForShowing.value = songItem
                            isShowViewSongDialog.value = true
                        })
                }

                2 -> {

                    TabScreenCategory(categorySongs = pagerSongsList[it].value.toMutableList(),
                        onItemClick = { item ->
                            if (!item.isAdded.value) {
                                categoryListForAdd.add(item.song)
                                item.isAdded.value = !item.isAdded.value
                            } else {
                                categoryListForAdd.remove(item.song)
                                item.isAdded.value = !item.isAdded.value
                            }
                            updateIsAddedState(
                                sortedFavoriteSong.value.toMutableList(), categoryListForAdd
                            )
                            updateIsAddedState(
                                sortedCategorySong.value.toMutableList(), categoryListForAdd
                            )
                        },
                        onItemLongPress = { songItem ->
                            selectedSongForShowing.value = songItem
                            isShowViewSongDialog.value = true
                        })
                }
            }
        }
    }
    SeeSongDialog(song = selectedSongForShowing.value, isShowDialog = isShowViewSongDialog)
}

private fun updateIsAddedState(
    addSongs: MutableList<AddSong>, categoryListForAdd: SnapshotStateList<Song>
) {
    addSongs.forEach { addSong ->
        addSong.isAdded.value = categoryListForAdd.contains(addSong.song)
    }
}