package ru.betel.app.ui.drawer_layout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.betel.app.ui.screens.category.CategoryScreen
import ru.betel.app.ui.screens.edit.song.EditSongScreen
import ru.betel.app.ui.screens.edit.template.EditTemplateScreen
import ru.betel.app.ui.screens.favorite.FavoriteScreen
import ru.betel.app.ui.screens.home.HomeScreen
import ru.betel.app.ui.screens.new_song.NewSongScreen
import ru.betel.app.ui.screens.new_template.NewTemplateScreen
import ru.betel.app.ui.screens.single_song.SingleSongScreen
import ru.betel.app.ui.screens.single_template.SingleTemplateScreen
import ru.betel.app.ui.screens.template.TemplateScreen
import ru.betel.app.ui.screens.templates_song.TemplatesSongScreen
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppMainContent(
    navController: NavHostController,
    actionBarState: MutableState<ActionBarState>,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel
) {
    val isFrom = remember {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize()) {

        NavHost(
            navController = navController,
            startDestination = Screens.TEMPLATE_SCREEN.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screens.HOME_SCREEN.route) {
                HomeScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    settingViewModel = settingViewModel,
                    editViewModel = editViewModel,
                    viewModel = songViewModel
                )
            }
            composable(Screens.TEMPLATE_SCREEN.route) {
                TemplateScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    viewModel = templateViewModel, settingViewModel = settingViewModel,
                )
            }
            composable(Screens.NEW_SONG_SCREEN.route) {
                NewSongScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    settingViewModel = settingViewModel,
                    songViewModel = songViewModel
                )
            }
            composable(Screens.CATEGORY_SCREEN.route) {
                CategoryScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    settingViewModel = settingViewModel,
                    viewModel = songViewModel
                )
            }
            composable(Screens.FAVORITE_SCREEN.route) {
                FavoriteScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    settingViewModel = settingViewModel,
                    viewModel = songViewModel
                )
            }
            composable(Screens.NEW_TEMPLATE_SCREEN.route) {
                NewTemplateScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    songViewModel = songViewModel,
                    templateViewModel = templateViewModel,
                    settingViewModel = settingViewModel
                )
            }
            composable(Screens.SINGLE_TEMPLATE_SCREEN.route) {
                SingleTemplateScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    settingViewModel = settingViewModel,
                    templateViewModel = templateViewModel,
                    songViewModel = songViewModel
                )
            }

            composable(Screens.SINGLE_SONG_SCREEN.route) {
                SingleSongScreen(
                    isSongFromTemplate = isFrom,
                    navController = navController,
                    actionBarState = actionBarState,
                    viewModel = songViewModel,
                    templateViewModel = templateViewModel,
                    settingViewModel = settingViewModel,
                )
            }
            composable(Screens.TEMPLATES_SONGS_SCREEN.route) {
                TemplatesSongScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    templateViewModel = templateViewModel,
                    settingViewModel = settingViewModel,
                    songViewModel = songViewModel,
                    scope = coroutineScope
                )
            }

            composable(Screens.EDIT_SONG_SCREEN.route) {
                EditSongScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    settingViewModel = settingViewModel,
                    editViewModel = editViewModel
                )
            }
            composable(Screens.EDIT_TEMPLATE_SCREEN.route) {
                EditTemplateScreen(
                    navController = navController,
                    actionBarState = actionBarState,
                    songViewModel = songViewModel,
                    templateViewModel = templateViewModel,
                    settingViewModel = settingViewModel,
                    editViewModel =editViewModel
                )
            }
        }
    }
}

