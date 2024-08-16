package ru.betel.app.ui.drawer_layout

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.betel.app.ui.widgets.pop_up.DeleteSongDialog
import ru.betel.app.ui.widgets.pop_up.DeleteTemplateDialog
import ru.betel.app.ui.widgets.pop_up.SendNotificationDialog
import ru.betel.app.ui.widgets.pop_up.TemplateSaveMode
import ru.betel.app.ui.widgets.pop_up.UploadDialog
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.Screens
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
    actionBarState: MutableState<ActionBarState>,
    onSettingsBtnClick: () -> Unit,
    onLogInBtnClick: () -> Unit,
    appTheme: AppTheme,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val deleteSongDialogState = remember { mutableStateOf(false) }
    val sendNotificationDialogState = remember { mutableStateOf(false) }
    val uploadDialogState = remember { mutableStateOf(false) }
    val deleteTemplateDialogState = remember { mutableStateOf(false) }
    val songState = remember {
        mutableStateOf<Song>(
            Song(
                id = "",
                title = "",
                tonality = "",
                words = "",
                temp = "",
                isFromSongbookSong = false,
                isGlorifyingSong = false,
                isWorshipSong = false,
                isGiftSong = false
            )
        )
    }
    val templateState = remember {
        mutableStateOf<SongTemplate>(
            SongTemplate(
                "", "", "", "", false, emptyList(), emptyList(), emptyList(), emptyList()
            )
        )
    }
    val allSongs = songViewModel.allSongState.collectAsState(initial = listOf())
    val screenState = remember {
        mutableStateOf(Screens.HOME_SCREEN)
    }


    DeleteSongDialog(showDialog = deleteSongDialogState, song = songState, onConfirmationClick = {
        songViewModel.deleteSongFromFirebase(it, allSongs.value)
    }) {
        navController.popBackStack()
    }

    SendNotificationDialog(
        showDialog = sendNotificationDialogState,
        template = templateState,
        onConfirmationClick = {
            templateViewModel.sendNotification(it)
            sendNotificationDialogState.value = false
        })

    UploadDialog(
        appTheme = appTheme,
        templateViewModel = templateViewModel,
        showDialog = uploadDialogState,
        template = templateState,
    ) { template, isSendNotification ->
        templateViewModel.saveTemplateToFirebase(template)
        if (isSendNotification) {
            templateViewModel.sendNotification(template)
        }
        uploadDialogState.value = false
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

    Scaffold(drawerContent = {
        DrawerContent(screenState = screenState,
            scope = scope,
            appTheme = appTheme,
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
                screenState = screenState,
                navController = navController,
                actionBarState = actionBarState,
                songViewModel = songViewModel,
                templateViewModel = templateViewModel,
                settingViewModel = settingViewModel,
                editViewModel = editViewModel,
                searchAppBarState = searchAppBarState,
                templateState = templateState,
                songState = songState,
                deleteTemplateDialogState = deleteTemplateDialogState,
                sendNotificationDialogState = sendNotificationDialogState,
                uploadDialogState = uploadDialogState,
                deleteSongDialogState = deleteSongDialogState,
                onMenuBtnClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                onSettingsBtnClick = {
                    onSettingsBtnClick()
                },
            )
            it
        })


}


