package ru.betel.app.ui.drawer_layout

import android.os.Build
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
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.SearchAppBarState
import ru.betel.domain.model.ui.SongbookTextSize


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuDrawerLayout(
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
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
                    textSize = textSize, templateViewModel = templateViewModel,
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
                    onSettingsBtnClick = {
                        onSettingsBtnClick()
                    },
                    onShareBtnClick = {
                        songViewModel.shareSong(songViewModel.selectedSong.value)
                    })
            }

            ActionBarState.SINGLE_TEMPLATE_SCREEN -> {
                SingleTemplateActionBar(navController = navController,
                    settingViewModel = settingViewModel,
                    onSettingsBtnClick = {
                        onSettingsBtnClick()
                    },
                    onShareBtnClick = {
                        songViewModel.shareSong(songViewModel.selectedSong.value)
                    })
            }

            ActionBarState.NEW_SONG_SCREEN -> {
                NewSongActionBar(navController = navController)
            }

            ActionBarState.NEW_TEMPLATE_SCREEN -> {
                NewTemplateActionBar(navController = navController)
            }

            ActionBarState.FAVORITE_SCREEN -> {
                NewTemplateActionBar(navController = navController)
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

            MainContent(
                navController = navController,
                actionBarState = actionBarState,
                songViewModel = songViewModel,
                templateViewModel = templateViewModel,
                settingViewModel = settingViewModel
            )
            it
        })
}


