package ru.betel.app.ui.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.betel.app.ui.widgets.CategorizedLazyColumn
import ru.betel.app.ui.widgets.NothingFoundScreen
import ru.betel.app.ui.widgets.loading_anim.LoadingScreen
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongCategory
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import java.text.Collator
import java.util.Locale


@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: SongViewModel,
    settingViewModel: SettingViewModel,
) {
    actionBarState.value = ActionBarState.HOME_SCREEN

    val allSongState: State<List<Song>> =
        viewModel.allSongState.collectAsState(initial = emptyList())
    val searchAppBarText = viewModel.searchAppBarText

    val sortedSongs: State<List<Song>> = derivedStateOf {
        val songs = allSongState.value
        if (searchAppBarText.value.isNotBlank()) {
            songs.filter { song ->
                song.title.lowercase()
                    .contains(searchAppBarText.value.lowercase(), ignoreCase = true)
            }.sortedBy { song ->
                song.title.lowercase()
                song.words.lowercase()
            }
        } else {
            songs
        }
    }

    val isLoading = viewModel.isLoadingContainer
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading.value)



    LaunchedEffect(key1 = allSongState) {
        when (allSongState.value.isEmpty()) {
            true -> {
                viewModel.loadSong()
                viewModel.startTimer(15)
            }

            else -> {}
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = viewModel::loadSong,
        refreshTriggerDistance = 40.dp,
    ) {
        if (sortedSongs.value.isEmpty() && searchAppBarText.value.isEmpty()) {
            // showing loading animation
            LoadingScreen()
        } else if (sortedSongs.value.isNotEmpty()) {
            val armenianCollator = Collator.getInstance(Locale("hy"))
            val songTitles = sortedSongs.value.map { it.title }
            val sortedSongTitles = songTitles.sortedWith(armenianCollator)
            val _sortedSongs = sortedSongTitles.map { title ->
                sortedSongs.value.first { it.title == title }
            }

            val songs = _sortedSongs.groupBy {
                it.title.first()
            }.toSortedMap()

            val songsList = songs.map {
                SongCategory(items = it.value, charName = it.key.toString())
            }
            CategorizedLazyColumn(songsList,settingViewModel.songbookTextSize) { song ->
                viewModel.selectedSong.value = song
                navController.navigate(Screens.SINGLE_SONG_SCREEN.route)
            }
            /*
                        SongsList(songs = sortedSongs, settingViewModel.songbookTextSize) { song ->
                            viewModel.singleSong.value = song
                            navController.navigate(Screens.SINGLE_SONG_SCREEN.route)
                        }
            */

        } else if (sortedSongs.value.isEmpty() && searchAppBarText.value.isNotEmpty()) {
            // showing nothing found page
            NothingFoundScreen()
        }
    }






    BackHandler {
        Toast.makeText(navController.context, "Click one more time please", Toast.LENGTH_SHORT)
            .show()
    }
}
