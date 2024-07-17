package ru.betel.app.ui.screens.edit.template

import android.os.Build
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarTextColor
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.app.ui.widgets.AddNewSongToTemplate
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.SearchTopAppBar
import ru.betel.app.ui.widgets.dropdown_menu.WeekdayDropDownMenu
import ru.betel.app.ui.widgets.pop_up.TemplateSaveMode
import ru.betel.app.ui.widgets.snackbar.AppSnackbar
import ru.betel.app.ui.widgets.tabs.CategoryTabs
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.AddSong
import ru.betel.domain.model.ui.AddSongCategory
import ru.betel.domain.model.ui.NewTemplateFieldState
import ru.betel.test.DayPickerDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTemplateScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
    editViewModel: EditViewModel
) {
    val templateFieldState = remember { mutableStateOf(NewTemplateFieldState.INVALID_DAY) }
    val isShowingSaveDialog = remember { mutableStateOf(false) }
    Box {
        MainContent(
            navController = navController,
            actionBarState = actionBarState,
            songViewModel = songViewModel,
            templateViewModel = templateViewModel,
            editViewModel = editViewModel,
            settingViewModel = settingViewModel,
            isShowingDialog = isShowingSaveDialog,
            templateFieldState = templateFieldState,
        )

        AppSnackbar(
            isShowingSaveDialog,
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
    isShowingDialog: MutableState<Boolean>,
    templateFieldState: MutableState<NewTemplateFieldState>,
    editViewModel: EditViewModel
) {
    actionBarState.value = ActionBarState.NEW_TEMPLATE_SCREEN

    val currentTemplate = editViewModel.currentTemplate.value
    val saveMode = try {
        currentTemplate.id.toInt()
        TemplateSaveMode.LOCAL
    } catch (ex: Exception) {
        TemplateSaveMode.SERVER
    }

    val tempGlorifyingSongs = editViewModel.tempGlorifyingSongs
    val tempWorshipSongs = editViewModel.tempWorshipSongs
    val tempGiftSongs = editViewModel.tempGiftSongs

    val tempPerformerName = remember { mutableStateOf(currentTemplate.performerName) }
    val isSingleMode = remember { mutableStateOf(false) }
    val tempWeekday = editViewModel.tempWeekday
    val planningDay = editViewModel.planningDay


    LaunchedEffect(currentTemplate) {
        this.launch {
            tempWeekday.value = currentTemplate.weekday
            planningDay.value = currentTemplate.createDate

            tempGlorifyingSongs.addAll(currentTemplate.glorifyingSong)
            tempWorshipSongs.addAll(currentTemplate.worshipSong)
            tempGiftSongs.addAll(currentTemplate.giftSong)
        }
    }

    val isShowingDayDialog = remember { mutableStateOf(false) }

    val selectedCategory = remember { mutableStateOf(AddSongCategory.GLORIFYING) }
    val categorySongsList = when (selectedCategory.value) {
        AddSongCategory.GLORIFYING -> {
            templateViewModel.glorifyingAddSong.observeAsState(initial = mutableListOf())
        }

        AddSongCategory.WORSHIP -> {
            templateViewModel.worshipAddSong.observeAsState(initial = mutableListOf())
        }

        AddSongCategory.GIFT -> {
            templateViewModel.giftAddSong.observeAsState(initial = mutableListOf())
        }
    }
    var selectedCategoryForAddNewSong = when (selectedCategory.value) {
        AddSongCategory.GLORIFYING -> tempGlorifyingSongs

        AddSongCategory.WORSHIP -> tempWorshipSongs

        AddSongCategory.GIFT -> tempGiftSongs
    }
    val bottomSheetAllSongsForGlorifyingCategory: State<MutableList<AddSong>> =
        templateViewModel.tempGlorifyingAllAddSongs.observeAsState(mutableListOf())
    val bottomSheetAllSongsForWorshipCategory: State<MutableList<AddSong>> =
        templateViewModel.tempWorshipAllAddSongs.observeAsState(mutableListOf())
    val bottomSheetAllSongsForGiftCategory: State<MutableList<AddSong>> =
        templateViewModel.tempGiftAllAddSongs.observeAsState(mutableListOf())
    val bottomSheetFavoriteSong: State<MutableList<AddSong>> =
        templateViewModel.tempFavoriteAllAddSongs.observeAsState(mutableListOf())
    var selectedCategoryBottomSheetAllSongs = when (selectedCategory.value) {
        AddSongCategory.GLORIFYING -> bottomSheetAllSongsForGlorifyingCategory
        AddSongCategory.WORSHIP -> bottomSheetAllSongsForWorshipCategory
        AddSongCategory.GIFT -> bottomSheetAllSongsForGiftCategory
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
                Spacer(modifier = Modifier.width(6.dp))

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
                            fieldText = tempPerformerName,
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
                                selectedDay = tempWeekday, modifier = Modifier.fillMaxWidth(0.49f)
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
                                isShowing = isShowingDayDialog, dayState = planningDay
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
                    AddNewSongToTemplate(
                        categoryTitle = "Փառաբանություն", categorySongs = tempGlorifyingSongs
                    ) {
                        selectedCategory.value = AddSongCategory.GLORIFYING
                        selectedCategoryForAddNewSong = tempGlorifyingSongs
                        selectedCategoryBottomSheetAllSongs =
                            bottomSheetAllSongsForGlorifyingCategory
                        scope.launch { bottomSheetState.show() }
                    }
                    Spacer(modifier = Modifier.height(12.dp))


                    AddNewSongToTemplate(
                        categoryTitle = "Երկրպագություն", categorySongs = tempWorshipSongs
                    ) {
                        selectedCategory.value = AddSongCategory.WORSHIP
                        selectedCategoryForAddNewSong = tempWorshipSongs
                        selectedCategoryBottomSheetAllSongs = bottomSheetAllSongsForWorshipCategory
                        scope.launch { bottomSheetState.show() }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    AddNewSongToTemplate(
                        categoryTitle = "Ընծա", categorySongs = tempGiftSongs
                    ) {
                        selectedCategory.value = AddSongCategory.GIFT
                        selectedCategoryForAddNewSong = tempGiftSongs
                        selectedCategoryBottomSheetAllSongs = bottomSheetAllSongsForGiftCategory
                        scope.launch { bottomSheetState.show() }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    SaveButton {
                        templateViewModel.checkFields(
                            templateFieldState = templateFieldState,
                            tempPerformerName = tempPerformerName,
                            tempWeekday = tempWeekday,
                            planningDay = planningDay,
                            glorifyingSongsState = tempGlorifyingSongs,
                            worshipSongsState = tempWorshipSongs,
                            giftSongsState = tempGiftSongs
                        )
                        isShowingDialog.value = true
                        if (templateFieldState.value == NewTemplateFieldState.DONE) {
                            val editedTemplate = editViewModel.currentTemplate.value.copy(
                                createDate = planningDay.value,
                                performerName = tempPerformerName.value,
                                weekday = tempWeekday.value,
                                isSingleMode = false,
                                glorifyingSong = tempGlorifyingSongs.toList(),
                                worshipSong = tempWorshipSongs.toList(),
                                giftSong = tempGiftSongs.toList()
                            )



                            editViewModel.updateTemplate(
                                mode = saveMode,
                                old = editViewModel.currentTemplate.value,
                                new = editedTemplate
                            )
                            templateViewModel.loadTemplate()
                            templateViewModel.singleTemplate.value = editedTemplate
                            editViewModel.cleanFields()
                            navController.popBackStack()
                        }
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
                CategoryTabs(
                    categorySongs = categorySongsList.value,
                    categoryTitle = selectedCategory.value.title,
                    allSongs = selectedCategoryBottomSheetAllSongs,
                    searchAppBarText = songViewModel.searchAppBarText,
                    favoriteSongs = bottomSheetFavoriteSong,
                    categoryListForAdd = selectedCategoryForAddNewSong,
                ) {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }

            }
        })

    fun cleanAllFields() {
        editViewModel.cleanFields()
    }

    BackHandler {
        cleanAllFields()
        navController.popBackStack()
    }
}