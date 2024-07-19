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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.widgets.FavoriteSongsList
import ru.betel.app.ui.widgets.NothingFoundScreen
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



    SwipeRefresh(
        modifier = Modifier.fillMaxSize().background(appTheme.screenBackgroundColor),
        state = swipeRefreshState,
        onRefresh = viewModel::loadSong,
        refreshTriggerDistance = 40.dp,
    ) {
        if (sortedSongs.value.isEmpty() && searchAppBarText.value.isEmpty()) {
            Row(
                Modifier
                    .fillMaxWidth().background(appTheme.screenBackgroundColor)
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

            FavoriteSongsList(appTheme=appTheme,songs = sortedSongs.value,
                settingViewModel.songbookTextSize,
                onSongSelected = { song ->
                    viewModel.selectedSong.value = song
                    navController.navigate(Screens.SINGLE_SONG_SCREEN.route)
                }) {
                viewModel.deleteFavoriteSongs(it)
            }

        } else if (sortedSongs.value.isEmpty() && searchAppBarText.value.isNotEmpty()) {
            NothingFoundScreen(appTheme)
        }
    }


    BackHandler {
        navController.popBackStack()
    }

}