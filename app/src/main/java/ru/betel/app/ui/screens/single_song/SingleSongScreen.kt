package ru.betel.app.ui.screens.single_song

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState

private const val TAG = "HomeScreen"


@Composable
fun SingleSongScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    viewModel: SongViewModel,
    settingViewModel: SettingViewModel,
) {
    actionBarState.value = ActionBarState.SINGLE_SONG_SCREEN
    val scrollState = rememberScrollState()
    val song by viewModel.selectedSong.collectAsState()

    val appTheme by settingViewModel.appTheme

    Column(
        Modifier
            .fillMaxWidth().background(appTheme.screenBackgroundColor)
            .verticalScroll(scrollState),
    ) {
        Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)) {
            Text(
                text = song.title, style = TextStyle(
                    fontSize = settingViewModel.songbookTextSize.normalItemDefaultTextSize,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(700),
                    color = appTheme.primaryTextColor,
                ), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(0.7f)
            )

            Text(
                text = "${song.temp} / ${song.tonality}",
                style = TextStyle(
                    fontSize = settingViewModel.songbookTextSize.smallItemDefaultTextSize,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(700),
                    color = appTheme.secondaryTextColor,
                ),
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End,
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = song.words, style = TextStyle(
                fontSize = settingViewModel.songbookTextSize.normalItemDefaultTextSize,
                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                fontWeight = FontWeight(400),
                color = appTheme.primaryTextColor
            ), modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
        )
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