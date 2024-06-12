package ru.betel.app.ui.screens.templates_song

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.betel.app.ui.items.template_songs.TemplatesSongItem
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.ActionBarState

private const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TemplatesSongScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    templateViewModel: TemplateViewModel,
    songViewModel: SongViewModel,
    settingViewModel: SettingViewModel,
    scope: CoroutineScope
) {
    actionBarState.value = ActionBarState.SINGLE_SONG_SCREEN
    val template by templateViewModel.singleTemplate.collectAsState()
    val clickedSong by songViewModel.selectedSong.collectAsState()

    val currentSongIndex = remember {
        mutableStateOf(0)
    }
    val currentTemplateSongsList = mutableListOf<Song>().apply {
        addAll(template.glorifyingSong)
        addAll(template.worshipSong)
        addAll(template.giftSong)
    }

    val pagerState = rememberPagerState(pageCount = { currentTemplateSongsList.size })

    LaunchedEffect(key1 = currentTemplateSongsList) {
        currentTemplateSongsList.forEachIndexed { index, song ->
            if (song.title == clickedSong.title && song.words == clickedSong.words) {
                currentSongIndex.value = index
                scope.launch {
                    Log.e(
                        TAG,
                        "TemplatesSongScreen: currentSongIndex.value :: ${currentSongIndex.value}",
                    )
                    pagerState.scrollToPage(currentSongIndex.value, 0f)
                }
                return@LaunchedEffect
            }
        }
    }

    HorizontalPager(
        state = pagerState, modifier = Modifier.fillMaxSize()
    ) { page ->
        // Content for each page
        Log.e(TAG, "TemplatesSongScreen: index : $page")
        TemplatesSongItem(song = currentTemplateSongsList[page], settingViewModel.songbookTextSize)
    }

    BackHandler {
        navController.popBackStack()
    }
}

/*
******************* SUREN'S VERSION *******************
actionBarState.value = ActionBarState.SINGLE_SONG_SCREEN
    val scrollState = rememberScrollState()
    val song by viewModel.singleSong.collectAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
    ) {
        Text(
            text = song.title, style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                fontWeight = FontWeight(700),
                color = actionBarColor,
            ), textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "130 / ${song.tonality}",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(700),
                    color = textFieldPlaceholder,
                ),
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = song.words,
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                fontWeight = FontWeight(400),
                color = Color.Black.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }

    */


