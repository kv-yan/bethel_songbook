package ru.betel.app.ui.drawer_layout

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.betel.app.ui.action_bar.CategoryActionBar
import ru.betel.app.ui.action_bar.FavoriteActionBar
import ru.betel.app.ui.action_bar.HomeActionBar
import ru.betel.app.ui.action_bar.NewSongActionBar
import ru.betel.app.ui.action_bar.NewTemplateActionBar
import ru.betel.app.ui.action_bar.SingleSongActionBar
import ru.betel.app.ui.action_bar.SingleTemplateActionBar
import ru.betel.app.ui.action_bar.TemplateActionBar
import ru.betel.app.ui.widgets.pop_up.DeleteSongDialog
import ru.betel.app.ui.widgets.pop_up.DeleteTemplateDialog
import ru.betel.app.ui.widgets.pop_up.TemplateSaveMode
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.SearchAppBarState
import ru.betel.domain.model.ui.SongbookTextSize


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuDrawerLayout(
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel,
    navController: NavHostController,
    searchAppBarState: MutableState<SearchAppBarState>,
    textSize: SongbookTextSize,
    onSettingsBtnClick: () -> Unit,
    onLogInBtnClick: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    scaffoldState.drawerState
    val actionBarState = remember { mutableStateOf(ActionBarState.HOME_SCREEN) }
    val scope = rememberCoroutineScope()
    val deleteSongDialogState = remember { mutableStateOf(false) }
    val deleteTemplateDialogState = remember { mutableStateOf(false) }
    val songState =
        remember { mutableStateOf<Song>(Song("", "", "", "", "", false, false, false, false)) }
    val templateState = remember {
        mutableStateOf<SongTemplate>(
            SongTemplate(
                "", "", "", "", false, emptyList(), emptyList(), emptyList(), emptyList()
            )
        )
    }


    DeleteSongDialog(showDialog = deleteSongDialogState, song = songState, onConfirmationClick = {
        songViewModel.deleteSongFromFirebase(it)
    }) {
        navController.popBackStack()
    }
    val saveMode = mutableStateOf<TemplateSaveMode>(
            try {
                templateState.value.id.toInt()
                TemplateSaveMode.LOCAL
            } catch (ex: Exception) {
                TemplateSaveMode.SERVER
            }

        )

    DeleteTemplateDialog(mode = saveMode.value,
        showDialog = deleteTemplateDialogState,
        template = templateState,
        onConfirmationClick = { template, mode ->
            when (mode) {
                TemplateSaveMode.LOCAL -> {
                    templateViewModel.deleteTemplateFromLocal(template)
                }

                TemplateSaveMode.SERVER -> {
                    templateViewModel.deleteTemplateFromFirebase(template)
                }
            }
        }) {
        navController.popBackStack()
    }

    Scaffold(topBar = {
        when (actionBarState.value) {
            ActionBarState.HOME_SCREEN -> {
                HomeActionBar(navController = navController,
                    searchAppBarState = searchAppBarState,
                    songViewModel = songViewModel,
                    settingViewModel = settingViewModel,
                    textSize = textSize,
                    onMenuIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    onSettingsBtnClick = {
                        onSettingsBtnClick()
                    })
            }

            ActionBarState.TEMPLATE_SCREEN -> {
                TemplateActionBar(navController = navController,
                    searchAppBarState = searchAppBarState,
                    textSize = textSize,
                    templateViewModel = templateViewModel,
                    settingViewModel = settingViewModel,
                    onSettingsBtnClick = {
                        onSettingsBtnClick()
                    },
                    onMenuIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
            }

            ActionBarState.CATEGORY_SCREEN -> {
                CategoryActionBar(navController = navController,
                    searchAppBarState = searchAppBarState,
                    viewModel = songViewModel,
                    settingViewModel = settingViewModel,
                    onSettingsBtnClick = {
                        onSettingsBtnClick()
                    },
                    textSize = textSize,
                    onMenuIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })

            }

            ActionBarState.FAVORITE_SCREEN -> {
                FavoriteActionBar(navController = navController,
                    searchAppBarState = searchAppBarState,
                    songViewModel = songViewModel,
                    settingViewModel = settingViewModel,
                    textSize = textSize,
                    onMenuIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    onSettingsBtnClick = {
                        onSettingsBtnClick()
                    })

            }

            ActionBarState.SINGLE_SONG_SCREEN -> {
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
            }

            ActionBarState.SINGLE_TEMPLATE_SCREEN -> {
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
                    })
            }

            ActionBarState.NEW_SONG_SCREEN -> {
                NewSongActionBar(navController = navController)
            }

            ActionBarState.NEW_TEMPLATE_SCREEN -> {
                NewTemplateActionBar(
                    navController = navController,
                    editViewModel = editViewModel,
                    templateViewModel = templateViewModel
                )
            }

            ActionBarState.FAVORITE_SCREEN -> {
                NewTemplateActionBar(
                    navController = navController,
                    editViewModel = editViewModel,
                    templateViewModel = templateViewModel
                )
            }
        }
    },
        drawerContent = {

            DrawerContent(scope = scope,
                drawerState = scaffoldState.drawerState,
                navController = navController,
                onLogInBtnClick = {
                    onLogInBtnClick()
                })
        },
        drawerBackgroundColor = Color.White,
        drawerGesturesEnabled = true,
        drawerContentColor = Color.Black,
        scaffoldState = scaffoldState,
        content = {

            AppMainContent(
                navController = navController,
                actionBarState = actionBarState,
                songViewModel = songViewModel,
                templateViewModel = templateViewModel,
                settingViewModel = settingViewModel,
                editViewModel = editViewModel
            )
            it
        })
}


