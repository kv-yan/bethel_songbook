package ru.betel.app.ui.screens.category


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.betel.app.ui.widgets.NothingFoundScreen
import ru.betel.app.ui.widgets.SongsList
import ru.betel.app.ui.widgets.loading_anim.LoadingScreen
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import ru.betel.domain.model.ui.SongsCategory

@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: SongViewModel,
    settingViewModel: SettingViewModel
) {
    actionBarState.value = ActionBarState.CATEGORY_SCREEN
    val appTheme = settingViewModel.appTheme.value

    val categorySongs = when (viewModel.selectedCategory.collectAsState().value) {
        SongsCategory.ALL -> viewModel.allSongState.collectAsState(emptyList())
        SongsCategory.GLORIFYING -> viewModel.glorifyingSongsState.collectAsState(emptyList())
        SongsCategory.WORSHIP -> viewModel.worshipSongsState.collectAsState(emptyList())
        SongsCategory.GIFT -> viewModel.giftSongsSongsState.collectAsState(emptyList())
        SongsCategory.FROM_SONGBOOK -> viewModel.fromSongbookState.collectAsState(emptyList())
    }

    val searchAppBarText = viewModel.searchAppBarText

    val sortedSongs: State<List<Song>> = derivedStateOf {
        val songs = categorySongs.value
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

    val isLoading = viewModel.isLoadingContainer.value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val favoriteSongs = viewModel.favoriteSongs.observeAsState(initial = emptyList())

    SwipeRefresh(
        modifier = Modifier.background(appTheme.screenBackgroundColor),
        state = swipeRefreshState,
        onRefresh = viewModel::loadSong,
        refreshTriggerDistance = 40.dp,
    ) {
        if (sortedSongs.value.isEmpty() && searchAppBarText.value.isEmpty()) {
            LoadingScreen(appTheme)
        } else if (sortedSongs.value.isNotEmpty()) {
            // showing all songs
            SongsList(appTheme = appTheme,
                songs = sortedSongs.value,
                favoriteSongs = favoriteSongs.value,
                isEnableLongPress = true,
                isForCategory = true,
                songbookTextSize = settingViewModel.songbookTextSize,
                onEditClick = {},
                onShareClick = {},
                onFavoriteClick = { song, isAdded ->
                    if (isAdded) {
                        viewModel.deleteFavoriteSongs(song)
                    } else {
                        viewModel.insertFavoriteSongs(song)
                    }
                },
                onDeleteClick = {}) { song, index ->
                viewModel.selectedSong.value = song
                viewModel.selectedSongIndex.value = index
                viewModel.selectedSongList.value = sortedSongs.value
                navController.navigate(Screens.SINGLE_SONG_SCREEN.route)
            }

        } else if (sortedSongs.value.isEmpty() && searchAppBarText.value.isNotEmpty()) {
            // showing nothing found page
            NothingFoundScreen(appTheme)
        }
    }


    BackHandler {
        navController.popBackStack()
    }

}