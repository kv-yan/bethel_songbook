package ru.betel.app.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.betel.app.ui.DoubleBackToExitApp
import ru.betel.app.ui.widgets.CategorizedLazyColumn
import ru.betel.app.ui.widgets.NothingFoundScreen
import ru.betel.app.ui.widgets.loading_anim.LoadingScreen
import ru.betel.app.ui.widgets.pop_up.DeleteSongDialog
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongCategory
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern


@Composable
fun HomeScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: SongViewModel,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel
) {
    MainContent(
        navController = navController,
        actionBarState = actionBarState,
        viewModel = viewModel,
        settingViewModel = settingViewModel,
        editViewModel = editViewModel
    )
}


@SuppressLint("UnrememberedMutableState")
@Composable
private fun MainContent(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: SongViewModel,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel
) {
    actionBarState.value = ActionBarState.HOME_SCREEN

    val allSongState: State<List<Song>> =
        viewModel.allSongState.collectAsState(initial = emptyList())
    val searchAppBarText = viewModel.searchAppBarText

    val appTheme = settingViewModel.appTheme.value

    fun normalizeText(text: String): String {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replace("[\\p{M}]".toRegex(), "")
            .replace("[&\\/`՝#,+()$~%.'\":*?<>{}br0-9\\s]+".toRegex(), "")
            .lowercase(Locale.getDefault())
    }

    val regex = remember(searchAppBarText.value) {
        if (searchAppBarText.value.isNotBlank()) {
            val normalizedSearchText = normalizeText(searchAppBarText.value)
            val regexPattern = normalizedSearchText.map { ".*$it" }.joinToString("") + ".*"
            Pattern.compile(regexPattern)
        } else {
            null
        }
    }

    val filteredSongs: State<List<Song>> = derivedStateOf {
        val songs = allSongState.value
        if (regex != null) {
            songs.filter { song ->
                val normalizedTitle = normalizeText(song.title)
                regex.matcher(normalizedTitle).matches()
            }
        } else {
            songs
        }
    }

    val isLoading = viewModel.isLoadingContainer
    val isDeletingSong = remember { mutableStateOf(false) }
    val deletingSong =
        remember { mutableStateOf(Song("0", "0", "0", "0", "0", false, false, false, false)) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading.value)

    LaunchedEffect(key1 = allSongState) {
        when (allSongState.value.isEmpty()) {
            true -> {
                viewModel.loadSong()
                viewModel.startTimer(7)
            }

            else -> {}
        }
    }

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .background(appTheme.screenBackgroundColor),
        state = swipeRefreshState,
        onRefresh = viewModel::loadSong,
        refreshTriggerDistance = 40.dp,
    ) {
        when {
            filteredSongs.value.isEmpty() && searchAppBarText.value.isEmpty() -> {
                LoadingScreen(appTheme)
            }

            filteredSongs.value.isNotEmpty() -> {
                val songs = filteredSongs.value.groupBy {
                    it.title.first()
                }.toSortedMap()

                val songsList = songs.map {
                    SongCategory(items = it.value, charName = it.key.toString())
                }

                CategorizedLazyColumn(
                    appTheme = appTheme,
                    categories = songsList,
                    textSize = settingViewModel.songbookTextSize,
                    onEditClick = {
                        editViewModel.currentSong.value = it
                        navController.navigate(Screens.EDIT_SONG_SCREEN.route)
                    },
                    onShareClick = {
                        viewModel.shareSong(it)
                    },
                    onDeleteClick = {
                        isDeletingSong.value = true
                        deletingSong.value = it
                    }) { song ->
                    viewModel.selectedSong.value = song
                    navController.navigate(Screens.SINGLE_SONG_SCREEN.route)
                }
            }

            filteredSongs.value.isEmpty() && searchAppBarText.value.isNotEmpty() -> {
                NothingFoundScreen(appTheme)
            }
        }
    }

    DeleteSongDialog(showDialog = isDeletingSong, song = deletingSong, onUpdateSongs = {
        viewModel.loadSong()
    }, onConfirmationClick = {
        viewModel.deleteSongFromFirebase(it)
    })
    DoubleBackToExitApp()
}