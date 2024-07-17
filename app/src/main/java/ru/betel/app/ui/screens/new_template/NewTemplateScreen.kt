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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.betel.app.R
import ru.betel.app.ui.screens.new_song.cleanFieldsValues
import ru.betel.app.ui.theme.actionBarTextColor
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.app.ui.widgets.AddNewSongToTemplate
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.SearchTopAppBar
import ru.betel.app.ui.widgets.dropdown_menu.WeekdayDropDownMenu
import ru.betel.app.ui.widgets.pop_up.CheckTemplatePropertiesDialog
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
import ru.betel.domain.model.ui.NewTemplateFieldState
import ru.betel.test.DayPickerDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewTemplateScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
) {
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
                    .background(templateFieldState.value.bgColor, RoundedCornerShape(8.dp))
                    .padding(16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(if (templateFieldState.value == NewTemplateFieldState.DONE) R.drawable.ic_done else R.drawable.ic_error),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = templateFieldState.value.msg,
                    color = actionBarTextColor,
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
    val isShowingDayDialog = remember { mutableStateOf(false) }

    val selectedCategory = remember { mutableStateOf("Փառաբանություն") }
    val categorySongsList = when (selectedCategory.value) {
        "Փառաբանություն" -> {
            templateViewModel.glorifyingAddSong.observeAsState(initial = mutableListOf())
        }

        "Երկրպագություն" -> {
            templateViewModel.worshipAddSong.observeAsState(initial = mutableListOf())
        }

        "Ընծա" -> {
            templateViewModel.giftAddSong.observeAsState(initial = mutableListOf())
        }

        else -> {
            templateViewModel.singleModeAddSong.observeAsState(initial = mutableListOf())
        }
    }
    val selectedCategoryForAddNewSong = when (selectedCategory.value) {
        "Փառաբանություն" -> templateViewModel.tempGlorifyingSongs
        "Երկրպագություն" -> templateViewModel.tempWorshipSongs
        "Ընծա" -> templateViewModel.tempGiftSongs
        else -> templateViewModel.tempSingleModeSongs
    }
    val bottomSheetAllSongsForGlorifyingCategory: MutableState<MutableList<AddSong>> =
        templateViewModel.tempGlorifyingAllAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetAllSongsForWorshipCategory: MutableState<MutableList<AddSong>> =
        templateViewModel.tempWorshipAllAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetAllSongsForGiftCategory: MutableState<MutableList<AddSong>> =
        templateViewModel.tempGiftAllAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetAllSongsForSingleModeCategory: MutableState<MutableList<AddSong>> =templateViewModel.tempSingleModeAddSongs.observeAsState(mutableListOf()).toMutableState()
    val bottomSheetFavoriteSong: MutableState<MutableList<AddSong>> = templateViewModel.tempFavoriteAllAddSongs.observeAsState(mutableListOf()).toMutableState()
    var selectedCategoryBottomSheetAllSongs = when (selectedCategory.value) {
        "Փառաբանություն" -> bottomSheetAllSongsForGlorifyingCategory
        "Երկրպագություն" -> bottomSheetAllSongsForWorshipCategory
        "Ընծա" -> bottomSheetAllSongsForGiftCategory
        else -> bottomSheetAllSongsForSingleModeCategory
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
                color = MaterialTheme.colorScheme.background
            ) {
                actionBarState.value = ActionBarState.NEW_TEMPLATE_SCREEN
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                    ) {
                        MyTextFields(
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
                            }, shape = RoundedCornerShape(8.dp), color = fieldBg
                        ) {
                            WeekdayDropDownMenu(
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
                            color = fieldBg) {
                            DayPickerDialog(
                                isShowing = isShowingDayDialog,
                                dayState = templateViewModel.planningDay
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = songDividerColor)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (templateViewModel.isSingleMode.value) {
                        SingleModeSongs(
                            templateViewModel = templateViewModel,
                            selectedCategory = selectedCategory,
                            selectedCategoryForAddNewSong = selectedCategoryForAddNewSong,
                            selectedCategoryBottomSheetAllSongs = selectedCategoryBottomSheetAllSongs,
                            bottomSheetAllSongsForSingleModeCategory = bottomSheetAllSongsForSingleModeCategory,
                            bottomSheetState = bottomSheetState,
                            scope = scope
                        )
                    } else {
                        CategorizedSongs(
                            templateViewModel = templateViewModel,
                            selectedCategory = selectedCategory,
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

                    SaveButton {
                        templateViewModel.checkFields(templateFieldState)
                        isShowingSaveStateDialog.value =
                            templateFieldState.value != NewTemplateFieldState.DONE
                        val isSingleMode = templateViewModel.isSingleMode.value
                        val createdTemplate = SongTemplate(
                            id = "",
                            createDate = templateViewModel.planningDay.value,
                            performerName = templateViewModel.tempPerformerName.value,
                            weekday = templateViewModel.tempWeekday.value,
                            isSingleMode = isSingleMode,
                            glorifyingSong = if (!isSingleMode) templateViewModel.tempGlorifyingSongs.value ?: mutableListOf() else mutableListOf(),
                            worshipSong = if (!isSingleMode) templateViewModel.tempWorshipSongs.value ?: mutableListOf() else mutableListOf(),
                            giftSong = if (!isSingleMode) templateViewModel.tempGiftSongs.value ?: mutableListOf() else mutableListOf(),
                            singleModeSongs = if (isSingleMode) templateViewModel.tempSingleModeSongs.value ?: mutableListOf() else mutableListOf()
                        )

                        if (templateFieldState.value == NewTemplateFieldState.DONE) {
                            templateViewModel.createdNewTemplate.value = createdTemplate
                            if (FirebaseAuth.getInstance().currentUser != null) {
                                isShowingSaveModeDialog.value = true
                            } else {
                                templateViewModel.saveTemplateToLocalStorage(createdTemplate)
                                isShowingSaveStateDialog.value = true
                                navController.popBackStack()
                            }
                        }
                    }

                    CheckTemplatePropertiesDialog(
                        isShowingSaveModeDialog, templateViewModel
                    ) { mode,createdTemplate, isSendingNotification ->
                        when (mode) {
                            TemplateSaveMode.LOCAL -> {
                                templateViewModel.saveTemplateToLocalStorage(createdTemplate)
                            }

                            TemplateSaveMode.SERVER -> {
                                templateViewModel.saveTemplateToFirebase(createdTemplate)
                                if (isSendingNotification){
                                    // TODO: sent notification
                                }
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
                    onTextChange = {},
                    onCloseClicked = {},
                    textSize = settingViewModel.songbookTextSize
                )
                selectedCategoryForAddNewSong.value?.let {
                    CategoryTabs(
                        categorySongs = categorySongsList.value,
                        categoryTitle = selectedCategory.value,
                        allSongs = selectedCategoryBottomSheetAllSongs,
                        searchAppBarText = songViewModel.searchAppBarText,
                        favoriteSongs = bottomSheetFavoriteSong,
                        categoryListForAdd = it
                    ) {
                        scope.launch {
                            bottomSheetState.hide()
                        }
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategorizedSongs(
    templateViewModel: TemplateViewModel,
    selectedCategory: MutableState<String>,
    selectedCategoryForAddNewSong: MutableLiveData<SnapshotStateList<Song>>,
    selectedCategoryBottomSheetAllSongs: MutableState<MutableList<AddSong>>,
    bottomSheetAllSongsForGlorifyingCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForWorshipCategory: State<MutableList<AddSong>>,
    bottomSheetAllSongsForGiftCategory: State<MutableList<AddSong>>,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    templateViewModel.initCategorizedSongs()
    templateViewModel.tempGlorifyingSongs.value?.let {
        AddNewSongToTemplate(
            categoryTitle = "Փառաբանություն", categorySongs = it
        ) {
            selectedCategory.value = "Փառաբանություն"
            selectedCategoryForAddNewSong.postValue(templateViewModel.tempGlorifyingSongs.value)
            selectedCategoryBottomSheetAllSongs.value =
                bottomSheetAllSongsForGlorifyingCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))


    templateViewModel.tempWorshipSongs.value?.let {
        AddNewSongToTemplate(
            categoryTitle = "Երկրպագություն", categorySongs = it
        ) {
            selectedCategory.value = "Երկրպագություն"
            selectedCategoryForAddNewSong.value = templateViewModel.tempWorshipSongs.value
            selectedCategoryBottomSheetAllSongs.value = bottomSheetAllSongsForWorshipCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))

    templateViewModel.tempGiftSongs.value?.let {
        AddNewSongToTemplate(
            categoryTitle = "Ընծա", categorySongs = it
        ) {
            selectedCategory.value = "Ընծա"
            selectedCategoryForAddNewSong.value = templateViewModel.tempGiftSongs.value
            selectedCategoryBottomSheetAllSongs.value = bottomSheetAllSongsForGiftCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleModeSongs(
    templateViewModel: TemplateViewModel,
    selectedCategory: MutableState<String>,
    selectedCategoryForAddNewSong: MutableLiveData<SnapshotStateList<Song>>,
    selectedCategoryBottomSheetAllSongs: MutableState<MutableList<AddSong>>,
    bottomSheetAllSongsForSingleModeCategory: State<MutableList<AddSong>>,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    templateViewModel.initSingleMode()
    templateViewModel.tempSingleModeSongs.value?.let {
        AddNewSongToTemplate(
            categoryTitle = "Առանձնացված", categorySongs = it
        ) {
            selectedCategory.value = "Առանձնացված"
            selectedCategoryForAddNewSong.value = it
            selectedCategoryBottomSheetAllSongs.value =
                bottomSheetAllSongsForSingleModeCategory.value
            scope.launch { bottomSheetState.show() }
        }
    }
}

