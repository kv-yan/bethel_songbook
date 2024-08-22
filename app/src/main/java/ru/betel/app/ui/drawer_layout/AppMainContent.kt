package ru.betel.app.ui.drawer_layout

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.betel.app.ui.action_bar.CategoryActionBar
import ru.betel.app.ui.action_bar.FavoriteActionBar
import ru.betel.app.ui.action_bar.HomeActionBar
import ru.betel.app.ui.action_bar.NewSongActionBar
import ru.betel.app.ui.action_bar.NewTemplateActionBar
import ru.betel.app.ui.action_bar.SingleSongActionBar
import ru.betel.app.ui.action_bar.SingleTemplateActionBar
import ru.betel.app.ui.action_bar.TemplateActionBar
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
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import ru.betel.domain.model.ui.SearchAppBarState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppMainContent(
    navController: NavHostController,
    screenState: MutableState<Screens>,
    actionBarState: MutableState<ActionBarState>,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel,
    searchAppBarState: MutableState<SearchAppBarState>,
    onMenuBtnClick: () -> Unit,
    onSettingsBtnClick: () -> Unit,
    templateState: MutableState<SongTemplate>,
    songState: MutableState<Song>,
    deleteTemplateDialogState: MutableState<Boolean>,
    sendNotificationDialogState: MutableState<Boolean>,
    uploadDialogState: MutableState<Boolean>,
    deleteSongDialogState: MutableState<Boolean>,
) {
    val coroutineScope = rememberCoroutineScope()
    val modifier = Modifier.fillMaxSize()
    Column(
        Modifier
            .fillMaxSize()
            .background(settingViewModel.appTheme.value.backgroundColor)
    ) {

        NavHost(navController = navController,
            startDestination = Screens.NEW_TEMPLATE_SCREEN.route,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }) {
            composable(Screens.HOME_SCREEN.route) {
                screenState.value = Screens.HOME_SCREEN
                Column(modifier = modifier) {
                    HomeActionBar(navController = navController,
                        searchAppBarState = searchAppBarState,
                        songViewModel = songViewModel,
                        settingViewModel = settingViewModel,
                        textSize = settingViewModel.songbookTextSize,
                        appTheme = settingViewModel.appTheme.value,
                        onMenuIconClick = {
                            onMenuBtnClick()
                        },
                        onSettingsBtnClick = {
                            onSettingsBtnClick()
                        })
                    HomeScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        settingViewModel = settingViewModel,
                        editViewModel = editViewModel,
                        viewModel = songViewModel
                    )

                }
            }
            composable(Screens.TEMPLATE_SCREEN.route) {
                screenState.value = Screens.TEMPLATE_SCREEN
                Column(modifier = modifier) {
                    TemplateActionBar(navController = navController,
                        searchAppBarState = searchAppBarState,
                        textSize = settingViewModel.songbookTextSize,
                        templateViewModel = templateViewModel,
                        settingViewModel = settingViewModel,
                        onSettingsBtnClick = {
                            onSettingsBtnClick()
                        },
                        onMenuIconClick = {
                            onMenuBtnClick()
                        })

                    TemplateScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        viewModel = templateViewModel, settingViewModel = settingViewModel,
                    )
                }
            }
            composable(Screens.NEW_SONG_SCREEN.route) {
                screenState.value = Screens.NEW_SONG_SCREEN
                Column(modifier = modifier) {
                    NewSongActionBar(
                        navController = navController, appTheme = settingViewModel.appTheme.value
                    )

                    NewSongScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        settingViewModel = settingViewModel,
                        songViewModel = songViewModel
                    )
                }
            }
            composable(Screens.CATEGORY_SCREEN.route) {
                screenState.value = Screens.CATEGORY_SCREEN
                Column(modifier = modifier) {
                    CategoryActionBar(navController = navController,
                        searchAppBarState = searchAppBarState,
                        viewModel = songViewModel,
                        settingViewModel = settingViewModel,
                        onSettingsBtnClick = {
                            onSettingsBtnClick()
                        },
                        textSize = settingViewModel.songbookTextSize,
                        onMenuIconClick = {
                            onMenuBtnClick()
                        })

                    CategoryScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        settingViewModel = settingViewModel,
                        viewModel = songViewModel
                    )
                }
            }
            composable(Screens.FAVORITE_SCREEN.route) {
                screenState.value = Screens.FAVORITE_SCREEN
                Column(modifier = modifier) {
                    FavoriteActionBar(navController = navController,
                        searchAppBarState = searchAppBarState,
                        songViewModel = songViewModel,
                        settingViewModel = settingViewModel,
                        textSize = settingViewModel.songbookTextSize,
                        onMenuIconClick = {
                            onMenuBtnClick()
                        },
                        onSettingsBtnClick = {
                            onSettingsBtnClick()
                        })

                    FavoriteScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        settingViewModel = settingViewModel,
                        viewModel = songViewModel,
                        editViewModel = editViewModel
                    )
                }
            }
            composable(Screens.NEW_TEMPLATE_SCREEN.route) {
                screenState.value = Screens.NEW_TEMPLATE_SCREEN
                val tempGlorifyingSongs = remember { mutableStateListOf<Song>() }
                val tempWorshipSongs = remember { mutableStateListOf<Song>() }
                val tempGiftSongs = remember { mutableStateListOf<Song>() }
                val tempSingleModeSongs = remember { mutableStateListOf<Song>() }
                Column(modifier = modifier) {
                    NewTemplateActionBar(
                        navController = navController,
                        editViewModel = editViewModel,
                        templateViewModel = templateViewModel,
                        appTheme = settingViewModel.appTheme.value
                    ) {
                        tempGlorifyingSongs.clear()
                        tempWorshipSongs.clear()
                        tempGiftSongs.clear()
                        tempSingleModeSongs.clear()
                    }

                    NewTemplateScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        songViewModel = songViewModel,
                        templateViewModel = templateViewModel,
                        settingViewModel = settingViewModel,
                        tempGlorifyingSongs = tempGlorifyingSongs,
                        tempWorshipSongs = tempWorshipSongs,
                        tempGiftSongs = tempGiftSongs,
                        tempSingleModeSongs = tempSingleModeSongs
                    )
                }
                BackHandler {
                    Log.e("TAG", "check is working: back pres")
                    navController.popBackStack()
                }

            }
            composable(Screens.SINGLE_TEMPLATE_SCREEN.route) {
                screenState.value = Screens.SINGLE_TEMPLATE_SCREEN
                Column(modifier = modifier) {
                    SingleTemplateActionBar(navController = navController,
                        settingViewModel = settingViewModel,
                        editViewModel = editViewModel,
                        templateViewModel = templateViewModel,
                        onSettingsBtnClick = {
                            onSettingsBtnClick()
                        },
                        onShareBtnClick = {
                            templateViewModel.shareTemplate(templateViewModel.singleTemplate.value)
                        },
                        onDeleteBtnClick = {
                            templateState.value = it
                            deleteTemplateDialogState.value = true
                        },
                        onNotificationBtnClick = {
                            sendNotificationDialogState.value = true
                            templateState.value = it
                        },
                        onUploadBtnClick = {
                            uploadDialogState.value = true
                            templateState.value = it
                        })

                    SingleTemplateScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        settingViewModel = settingViewModel,
                        templateViewModel = templateViewModel,
                        songViewModel = songViewModel
                    )
                }
            }
            composable(Screens.SINGLE_SONG_SCREEN.route) {
                screenState.value = Screens.SINGLE_SONG_SCREEN
                Column(modifier = modifier) {
                    SingleSongActionBar(navController = navController,
                        settingViewModel = settingViewModel,
                        songViewModel = songViewModel,
                        editViewModel = editViewModel,
                        onSettingsBtnClick = {
                            onSettingsBtnClick()
                        },
                        onShareBtnClick = {
                            songViewModel.shareSong(songViewModel.selectedSong.value)
                        },
                        onDeleteBtnClick = {
                            songState.value = it
                            deleteSongDialogState.value = true
                        })

                    SingleSongScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        viewModel = songViewModel,
                        settingViewModel = settingViewModel,
                        editViewModel = editViewModel
                    )
                }
            }
            composable(Screens.TEMPLATES_SONGS_SCREEN.route) {
                Column(modifier = modifier) {
                    SingleSongActionBar(navController = navController,
                        settingViewModel = settingViewModel,
                        songViewModel = songViewModel,
                        editViewModel = editViewModel,
                        onSettingsBtnClick = {
                            onSettingsBtnClick()
                        },
                        onShareBtnClick = {
                            songViewModel.shareSong(songViewModel.selectedSong.value)
                        },
                        onDeleteBtnClick = {
                            songState.value = it
                            deleteSongDialogState.value = true
                        })

                    TemplatesSongScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        templateViewModel = templateViewModel,
                        settingViewModel = settingViewModel,
                        songViewModel = songViewModel,
                        editViewModel = editViewModel,
                        scope = coroutineScope
                    )
                }
            }
            composable(Screens.EDIT_SONG_SCREEN.route) {
                screenState.value = Screens.EDIT_SONG_SCREEN
                Column(modifier = modifier) {
                    NewSongActionBar(
                        navController = navController, appTheme = settingViewModel.appTheme.value
                    )
                    EditSongScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        settingViewModel = settingViewModel,
                        templateViewModel = templateViewModel,
                        songViewModel = songViewModel,
                        editViewModel = editViewModel
                    )
                }
            }
            composable(Screens.EDIT_TEMPLATE_SCREEN.route) {
                screenState.value = Screens.EDIT_TEMPLATE_SCREEN
                val tempGlorifyingSongs = remember { mutableStateListOf<Song>() }
                val tempWorshipSongs = remember { mutableStateListOf<Song>() }
                val tempGiftSongs = remember { mutableStateListOf<Song>() }
                val tempSingleModeSongs = remember { mutableStateListOf<Song>() }
                Column(modifier = modifier) {
                    NewTemplateActionBar(
                        navController = navController,
                        editViewModel = editViewModel,
                        templateViewModel = templateViewModel,
                        appTheme = settingViewModel.appTheme.value
                    ) {
                        editViewModel.tempGlorifyingSongs.clear()
                        editViewModel.tempWorshipSongs.clear()
                        editViewModel.tempGiftSongs.clear()
                        editViewModel.tempSingleModeSongs.clear()
                    }
                    EditTemplateScreen(
                        navController = navController,
                        actionBarState = actionBarState,
                        songViewModel = songViewModel,
                        templateViewModel = templateViewModel,
                        settingViewModel = settingViewModel,
                        editViewModel = editViewModel,
                    )
                }
            }
        }
    }
}

