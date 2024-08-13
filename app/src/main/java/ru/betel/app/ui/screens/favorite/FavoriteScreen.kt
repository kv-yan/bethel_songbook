package ru.betel.app.ui.screens.favorite


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.betel.app.R
import ru.betel.app.ui.widgets.FavoriteSongsList
import ru.betel.app.ui.widgets.NothingFoundScreen
import ru.betel.app.ui.widgets.pop_up.DeleteSongDialog
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens

@SuppressLint("UnrememberedMutableState")
@Composable
fun FavoriteScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: SongViewModel,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel
) {
    val appTheme = settingViewModel.appTheme.value
    actionBarState.value = ActionBarState.FAVORITE_SCREEN

    val categorySongs = viewModel.favoriteSongs.observeAsState(mutableListOf())

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
    val isShowingDeleteDialog = remember { mutableStateOf(false) }
    val songState =
        remember { mutableStateOf(Song("", "", "", "", "", false, false, false, false, false)) }



    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .background(appTheme.screenBackgroundColor),
        state = swipeRefreshState,
        onRefresh = viewModel::loadSong,
        refreshTriggerDistance = 40.dp,
    ) {
        if (sortedSongs.value.isEmpty() && searchAppBarText.value.isEmpty()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(appTheme.screenBackgroundColor)
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Չկան ընտրված երգեր", style = TextStyle(
                        fontSize = settingViewModel.songbookTextSize.normalItemDefaultTextSize,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                        fontWeight = FontWeight(500),
                        color = appTheme.primaryTextColor,
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_add_bookmark),
                    contentDescription = "image description",
                    tint = appTheme.primaryTextColor,
                    modifier = Modifier.size(34.dp)
                )

            }
        } else if (sortedSongs.value.isNotEmpty()) {
            FavoriteSongsList(appTheme = appTheme,
                songs = sortedSongs.value,
                songbookTextSize = settingViewModel.songbookTextSize,
                onSongSelected = { song, index ->
                    viewModel.selectedSong.value = song
                    viewModel.selectedSongIndex.value = index
                    viewModel.selectedSongList.value = sortedSongs.value
                    navController.navigate(Screens.SINGLE_SONG_SCREEN.route)
                },
                onEditClick = {
                    editViewModel.currentSong.value = it
                    navController.navigate(Screens.EDIT_SONG_SCREEN.route)
                },
                onShareClick = {
                    viewModel.shareSong(it)
                },
                onDeleteSong = {
                    isShowingDeleteDialog.value = true
                    songState.value = it
                },
                onRemoveFavSong = {
                    viewModel.deleteFavoriteSongs(it)
                })

        } else if (sortedSongs.value.isEmpty() && searchAppBarText.value.isNotEmpty()) {
            NothingFoundScreen(appTheme)
        }

        if (isShowingDeleteDialog.value) {
            val allSongs = viewModel.allSongState.collectAsState(initial = mutableListOf())
            DeleteSongDialog(showDialog = isShowingDeleteDialog,
                song = songState,
                onConfirmationClick = {
                    viewModel.deleteSongFromFirebaseWithoutId(
                        songState.value, allSongs.value
                    )
                    viewModel.deleteFavoriteSongs(songState.value)
                    isShowingDeleteDialog.value = false
                }) {
                viewModel.loadSong()
            }
        }
    }


    BackHandler {
        navController.popBackStack()
    }

}