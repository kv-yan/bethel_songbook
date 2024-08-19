package ru.betel.app.ui.screens.single_template

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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.betel.app.R
import ru.betel.app.ui.widgets.CategorySongs
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.Screens

@Composable
fun SingleTemplateScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    templateViewModel: TemplateViewModel,
    songViewModel: SongViewModel,
    settingViewModel: SettingViewModel,
) {
    actionBarState.value = ActionBarState.SINGLE_TEMPLATE_SCREEN
    val fontSize = settingViewModel.songbookTextSize.textFieldItemDefaultTextSize
    val template by templateViewModel.singleTemplate
    val rememberScrollState = rememberScrollState()
    val appTheme = settingViewModel.appTheme.value
    val isLocalTemplate = remember { mutableStateOf(false) }
    try {
        template.id.toInt()
        isLocalTemplate.value = true
    } catch (ex: NumberFormatException) {
        isLocalTemplate.value = false
    }

    Surface {
        Column(
            Modifier
                .fillMaxWidth()
                .background(appTheme.screenBackgroundColor)
                .verticalScroll(rememberScrollState)
        ) {
            if (!isLocalTemplate.value) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = template.performerName,
                        fontSize = fontSize,
                        fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                        fontWeight = FontWeight(500),
                        color = appTheme.secondaryTextColor,
                    )

                    Text(
                        text = "${template.weekday.substring(0..2)}. | ${template.createDate}",
                        fontSize = fontSize,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                        fontWeight = FontWeight(500),
                        color = appTheme.secondaryTextColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            if (template.isSingleMode) {
                SingleModeSongs(appTheme, settingViewModel, template, navController, songViewModel)
            } else {
                CategorizedSongs(appTheme, settingViewModel, template, navController, songViewModel)
            }

        }
    }


    BackHandler {
        navController.popBackStack()
    }
}


@Composable
fun CategorizedSongs(
    appTheme: AppTheme,
    settingViewModel: SettingViewModel,
    template: SongTemplate,
    navController: NavController,
    songViewModel: SongViewModel
) {
    val appTheme = settingViewModel.appTheme.value
    CategorySongs(
        appTheme = appTheme,
        fontSize = settingViewModel.songbookTextSize,
        categoryTitle = "Փառաբանություն",
        categorySongs = template.glorifyingSong
    ) {
//                navController.navigate(route = Screens.SINGLE_SONG_SCREEN.route)
        navController.navigate(route = Screens.TEMPLATES_SONGS_SCREEN.route)
        songViewModel.selectedSong.value = it
    }
    Spacer(modifier = Modifier.height(12.dp))
    CategorySongs(
        appTheme = appTheme,
        categoryTitle = "Երկրպագություն",
        fontSize = settingViewModel.songbookTextSize,
        categorySongs = template.worshipSong
    ) {
//                navController.navigate(route = Screens.SINGLE_SONG_SCREEN.route)
        navController.navigate(route = Screens.TEMPLATES_SONGS_SCREEN.route)
        songViewModel.selectedSong.value = it

    }
    Spacer(modifier = Modifier.height(12.dp))
    CategorySongs(
        appTheme = appTheme,
        categoryTitle = "Ընծա",
        fontSize = settingViewModel.songbookTextSize,
        categorySongs = template.giftSong
    ) {
//                navController.navigate(route = Screens.SINGLE_SONG_SCREEN.route)
        navController.navigate(route = Screens.TEMPLATES_SONGS_SCREEN.route)
        songViewModel.selectedSong.value = it
    }
}

@Composable
fun SingleModeSongs(
    appTheme: AppTheme,
    settingViewModel: SettingViewModel,
    template: SongTemplate,
    navController: NavController,
    songViewModel: SongViewModel
) {
    CategorySongs(
        appTheme,
        fontSize = settingViewModel.songbookTextSize,
        categoryTitle = "Փառաբանություն",
        isSingMode = true,
        categorySongs = template.singleModeSongs
    ) {
//                navController.navigate(route = Screens.SINGLE_SONG_SCREEN.route)
        navController.navigate(route = Screens.TEMPLATES_SONGS_SCREEN.route)
        songViewModel.selectedSong.value = it
    }
}