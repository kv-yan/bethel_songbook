package ru.betel.app.ui.screens.new_template

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.betel.app.R
import ru.betel.app.ui.widgets.AddNewSongToTemplate
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.SearchTopAppBar
import ru.betel.app.ui.widgets.dropdown_menu.WeekdayDropDownMenu
import ru.betel.app.ui.widgets.pop_up.CheckTemplatePropertiesDialog
import ru.betel.app.ui.widgets.pop_up.DayPickerDialog
import ru.betel.app.ui.widgets.pop_up.TemplateSaveMode
import ru.betel.app.ui.widgets.snackbar.AppSnackbar
import ru.betel.app.ui.widgets.tabs.CategoryTabs
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.data.extensions.toMutableState
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.AddSong
import ru.betel.domain.model.ui.AddSongCategory
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.NewTemplateFieldState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewTemplateScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
) {
    val appTheme = settingViewModel.appTheme.value
    val templateFieldState = remember { mutableStateOf(NewTemplateFieldState.INVALID_DAY) }
    val isShowingSaveStateDialog = remember { mutableStateOf(false) }
    val isShowingSaveModeDialog = remember { mutableStateOf(false) }
    Box {
        MainContent(
            navController = navController,
            actionBarState = actionBarState,
            songViewModel = songViewModel,
            templateViewModel = templateViewModel,
            settingViewModel = settingViewModel,
            isShowingSaveStateDialog = isShowingSaveStateDialog,
            templateFieldState = templateFieldState,
            isShowingSaveModeDialog = isShowingSaveModeDialog
        )

        AppSnackbar(
            isShowingSaveStateDialog,
            Modifier
                .offset(y = 40.dp)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appTheme.primaryButtonColor, RoundedCornerShape(8.dp))
                    .padding(16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(if (templateFieldState.value == NewTemplateFieldState.DONE) R.drawable.ic_done else R.drawable.ic_error),
                    contentDescription = null,
                    tint = appTheme.actionBarIconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = templateFieldState.value.msg,
                    color = appTheme.actionBarIconColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainContent(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
    templateFieldState: MutableState<NewTemplateFieldState>,
    isShowingSaveStateDialog: MutableState<Boolean>,
    isShowingSaveModeDialog: MutableState<Boolean>
) {
    val appTheme = settingViewModel.appTheme.value

    val isShowingDayDialog = remember { mutableStateOf(false) }

    val categoryState = remember { mutableStateOf(AddSongCategory.GLORIFYING) }
    val selectedCategorySongsList = when (categoryState.value) {
        AddSongCategory.GLORIFYING -> {
            templateViewModel.glorifyingAddSong.observeAsState(initial = mutableListOf())
        }

        AddSongCategory.WORSHIP -> {
            templateViewModel.worshipAddSong.observeAsState(initial = mutableListOf())
        }

        AddSongCategory.GIFT -> {
            templateViewModel.giftAddSong.observeAsState(initial = mutableListOf())
        }

        AddSongCategory.SingleMode -> {
            templateViewModel.singleModeAddSong.observeAsState(initial = mutableListOf())
        }
    }

    val tempGlorifyingSongs = remember { mutableStateListOf<Song>() }
    val tempWorshipSongs = remember { mutableStateListOf<Song>() }
    val tempGiftSongs = remember { mutableStateListOf<Song>() }
    val tempSingleModeSongs = remember { mutableStateListOf<Song>() }


    val selectedCategoryForAddNewSong = when (categoryState.value) {
        AddSongCategory.GLORIFYING -> tempGlorifyingSongs
        AddSongCategory.WORSHIP -> tempWorshipSongs
        AddSongCategory.GIFT -> tempGiftSongs
        AddSongCategory.SingleMode -> tempSingleModeSongs
    }
    val bottomSheetAllSongsForGlorifyingCategory: MutableState<MutableList<AddSong>> =
        templateViewModel.tempGlorifyingAllAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetAllSongsForWorshipCategory: MutableState<MutableList<AddSong>> =
        templateViewModel.tempWorshipAllAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetAllSongsForGiftCategory: MutableState<MutableList<AddSong>> =
        templateViewModel.tempGiftAllAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetAllSongsForSingleModeCategory: MutableState<MutableList<AddSong>> =
        templateViewModel.tempSingleModeAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetFavoriteSong: MutableState<MutableList<AddSong>> =
        templateViewModel.tempFavoriteAllAddSongs.observeAsState(mutableListOf()).toMutableState()

    val selectedCategoryBottomSheetAllSongs = when (categoryState.value) {
        AddSongCategory.GLORIFYING -> bottomSheetAllSongsForGlorifyingCategory
        AddSongCategory.WORSHIP -> bottomSheetAllSongsForWorshipCategory
        AddSongCategory.GIFT -> bottomSheetAllSongsForGiftCategory
        AddSongCategory.SingleMode -> bottomSheetAllSongsForSingleModeCategory
    }
    val verticalScrollState = rememberScrollState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScrollState),
                color = appTheme.screenBackgroundColor
            ) {
                actionBarState.value = ActionBarState.NEW_TEMPLATE_SCREEN
                Column(
                    Modifier.fillMaxSize()
                ) {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                    ) {
                        MyTextFields(
                            appTheme = appTheme,
                            placeholder = "Առաջնորդ",
                            fieldText = templateViewModel.tempPerformerName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(25.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                    ) {
                        Surface(
                            modifier = Modifier.clickable {
                                songViewModel.isDropdownMenuVisible.value = true
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = appTheme.fieldBackgroundColor
                        ) {
                            WeekdayDropDownMenu(
                                appTheme = appTheme,
                                selectedDay = templateViewModel.tempWeekday,
                                modifier = Modifier.fillMaxWidth(0.49f)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Surface(modifier = Modifier
                            .clickable {
                                isShowingDayDialog.value = true
                            }
                            .fillMaxWidth()
                            .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = appTheme.fieldBackgroundColor) {
                            DayPickerDialog(
                                appTheme = appTheme,
                                isShowing = isShowingDayDialog,
                                dayState = templateViewModel.planningDay
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = appTheme.dividerColor)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (templateViewModel.isSingleMode.value) {
                        SingleModeSongs(
                            appTheme = appTheme,
                            selectedCategory = categoryState,
                            singleModeSongs = tempSingleModeSongs,
                            templateViewModel = templateViewModel,
                            selectedCategoryForAddNewSong = selectedCategoryForAddNewSong,
                            selectedCategoryBottomSheetAllSongs = selectedCategoryBottomSheetAllSongs,
                            bottomSheetAllSongsForSingleModeCategory = bottomSheetAllSongsForSingleModeCategory,
                            bottomSheetState = bottomSheetState,
                            scope = scope
                        )
                    } else {
                        CategorizedSongs(
                            appTheme = appTheme,
                            templateViewModel = templateViewModel,
                            selectedCategory = categoryState,
                            tempGlorifyingSongs = tempGlorifyingSongs,
                            tempWorshipSongs = tempWorshipSongs,
                            tempGiftSongs = tempGiftSongs,
                            selectedCategoryForAddNewSong = selectedCategoryForAddNewSong,
                            selectedCategoryBottomSheetAllSongs = selectedCategoryBottomSheetAllSongs,
                            bottomSheetAllSongsForGlorifyingCategory = bottomSheetAllSongsForGlorifyingCategory,
                            bottomSheetAllSongsForWorshipCategory = bottomSheetAllSongsForWorshipCategory,
                            bottomSheetAllSongsForGiftCategory = bottomSheetAllSongsForGiftCategory,
                            bottomSheetState = bottomSheetState,
                            scope = scope
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SaveButton(appTheme = appTheme) {
                        templateViewModel.checkFields(
                            templateFieldState = templateFieldState,
                            tempGlorifyingSongs = tempGlorifyingSongs,
                            tempWorshipSongs = tempWorshipSongs,
                            tempGiftSongs = tempGiftSongs,
                            tempSingleModeSongs = tempSingleModeSongs
                        )
                        isShowingSaveStateDialog.value =
                            templateFieldState.value != NewTemplateFieldState.DONE
                        val isSingleMode = templateViewModel.isSingleMode.value
                        val createdTemplate = SongTemplate(
                            id = "",
                            createDate = templateViewModel.planningDay.value,
                            performerName = templateViewModel.tempPerformerName.value,
                            weekday = templateViewModel.tempWeekday.value,
                            isSingleMode = isSingleMode,
                            glorifyingSong = if (!isSingleMode) tempGlorifyingSongs.toList() else mutableListOf(),
                            worshipSong = if (!isSingleMode) tempWorshipSongs.toList() else mutableListOf(),
                            giftSong = if (!isSingleMode) tempGiftSongs.toList() else mutableListOf(),
                            singleModeSongs = if (isSingleMode) tempSingleModeSongs else mutableListOf()
                        )

                        if (templateFieldState.value == NewTemplateFieldState.DONE) {
                            templateViewModel.createdNewTemplate.value = createdTemplate
                            if (FirebaseAuth.getInstance().currentUser != null) {
                                isShowingSaveModeDialog.value = true

                            } else {
                                templateViewModel.saveTemplateToLocalStorage(createdTemplate)
                                isShowingSaveStateDialog.value = true
                                navController.popBackStack()
                                templateViewModel.cleanFields()
                            }
                        }
                    }

                    CheckTemplatePropertiesDialog(
                        isShowDialog = isShowingSaveModeDialog, viewModel = templateViewModel
                    ) { mode, createdTemplate, isSendingNotification ->
                        when (mode) {
                            TemplateSaveMode.LOCAL -> {
                                templateViewModel.saveTemplateToLocalStorage(createdTemplate)
                                templateViewModel.cleanFields()
                            }

                            TemplateSaveMode.SERVER -> {
                                templateViewModel.saveTemplateToFirebase(createdTemplate)
                                if (isSendingNotification) {
                                    templateViewModel.sendNotification(createdTemplate)
                                }
                                templateViewModel.cleanFields()
                            }
                        }
                        isShowingSaveModeDialog.value = false
                        isShowingSaveStateDialog.value = true
                        navController.popBackStack()
                    }

                }
            }
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                Spacer(modifier = Modifier.height(12.dp))
                SearchTopAppBar(text = songViewModel.searchAppBarText,
                    onTextChange = {
                        songViewModel.searchAppBarText.value = it
                    },
                    onCloseClicked = {},
                    textSize = settingViewModel.songbookTextSize
                )
                CategoryTabs(
                    categorySongs = selectedCategorySongsList.value,
                    categoryTitle = categoryState.value.title,
                    allSongs = selectedCategoryBottomSheetAllSongs,
                    searchAppBarText = songViewModel.searchAppBarText,
                    favoriteSongs = bottomSheetFavoriteSong,
                    categoryListForAdd = selectedCategoryForAddNewSong
                ) {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }
            }
        })
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategorizedSongs(
    appTheme: AppTheme,
    templateViewModel: TemplateViewModel,
    tempGlorifyingSongs: SnapshotStateList<Song>,
    tempWorshipSongs: SnapshotStateList<Song>,
    tempGiftSongs: SnapshotStateList<Song>,
    selectedCategory: MutableState<AddSongCategory>,
    selectedCategoryForAddNewSong: SnapshotStateList<Song>,
    selectedCategoryBottomSheetAllSongs: MutableState<MutableList<AddSong>>,
    bottomSheetAllSongsForGlorifyingCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForWorshipCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForGiftCategory: State<MutableList<AddSong>>,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    templateViewModel.initCategorizedSongs()
    tempGlorifyingSongs.let {
        AddNewSongToTemplate(
            appTheme = appTheme, categoryTitle = "Փառաբանություն", categorySongs = it
        ) {
            selectedCategory.value = AddSongCategory.GLORIFYING
//            selectedCategoryForAddNewSong.addAll(it)
            selectedCategoryBottomSheetAllSongs.value =
                bottomSheetAllSongsForGlorifyingCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))


    tempWorshipSongs.let {
        AddNewSongToTemplate(
            appTheme = appTheme, categoryTitle = "Երկրպագություն", categorySongs = it
        ) {
            selectedCategory.value = AddSongCategory.WORSHIP
//            selectedCategoryForAddNewSong.addAll(it)
            selectedCategoryBottomSheetAllSongs.value = bottomSheetAllSongsForWorshipCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))

    tempGiftSongs.let {
        AddNewSongToTemplate(
            appTheme = appTheme, categoryTitle = "Ընծա", categorySongs = it
        ) {
            selectedCategory.value = AddSongCategory.GIFT
//            selectedCategoryForAddNewSong.addAll(it)
            selectedCategoryBottomSheetAllSongs.value = bottomSheetAllSongsForGiftCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleModeSongs(
    appTheme: AppTheme,
    singleModeSongs: SnapshotStateList<Song>,
    templateViewModel: TemplateViewModel,
    selectedCategory: MutableState<AddSongCategory>,
    selectedCategoryForAddNewSong: SnapshotStateList<Song>,
    selectedCategoryBottomSheetAllSongs: MutableState<MutableList<AddSong>>,
    bottomSheetAllSongsForSingleModeCategory: State<MutableList<AddSong>>,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    templateViewModel.initSingleMode()
    singleModeSongs.let {
        AddNewSongToTemplate(
            appTheme = appTheme, categoryTitle = "Առանձնացված", categorySongs = it
        ) {
            selectedCategory.value = AddSongCategory.SingleMode
//            selectedCategoryForAddNewSong.addAll(it)
            selectedCategoryBottomSheetAllSongs.value = bottomSheetAllSongsForSingleModeCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
}

