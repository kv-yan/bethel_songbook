package ru.betel.app.ui.screens.new_template

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.app.ui.widgets.AddNewSongToTemplate
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.SearchTopAppBar
import ru.betel.app.ui.widgets.dropdown_menu.WeekdayDropDownMenu
import ru.betel.app.ui.widgets.tabs.CategoryTabs
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.AddSong

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewTemplateScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    songViewModel: SongViewModel,
    templateViewModel: TemplateViewModel,
    settingViewModel: SettingViewModel,
) {
    val selectedCategory = remember { mutableStateOf("Փառաբանություն") }
    println("value :: ${selectedCategory.value}")
    val categorySongsList = when (selectedCategory.value) {
        "Փառաբանություն" -> {
            templateViewModel.glorifyingAddSong.observeAsState(initial = mutableListOf())
        }

        "Երկրպագություն" -> {
            templateViewModel.worshipAddSong.observeAsState(initial = mutableListOf())
        }

        else -> {
            templateViewModel.giftAddSong.observeAsState(initial = mutableListOf())
        }
    }


    var selectedCategoryForAddNewSong = when (selectedCategory.value) {
        "Փառաբանություն" -> templateViewModel.tempGlorifyingSongs

        "Երկրպագություն" -> templateViewModel.tempWorshipSongs

        else -> templateViewModel.tempGiftSongs
    }


    val bottomSheetAllSongsForGlorifyingCategory: State<MutableList<AddSong>> =
        templateViewModel.tempGlorifyingAllAddSongs.observeAsState(mutableListOf())
    val bottomSheetAllSongsForWorshipCategory: State<MutableList<AddSong>> =
        templateViewModel.tempWorshipAllAddSongs.observeAsState(mutableListOf())
    val bottomSheetAllSongsForGiftCategory: State<MutableList<AddSong>> =
        templateViewModel.tempGiftAllAddSongs.observeAsState(
            mutableListOf()
        )
    val bottomSheetFavoriteSong: State<MutableList<AddSong>> =
        templateViewModel.tempFavoriteAllAddSongs.observeAsState(mutableListOf())
    var selectedCategoryBottomSheetAllSongs = when (selectedCategory.value) {
        "Փառաբանություն" -> bottomSheetAllSongsForGlorifyingCategory

        "Երկրպագություն" -> bottomSheetAllSongsForWorshipCategory

        else -> bottomSheetAllSongsForGiftCategory
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
                                .fillMaxWidth(0.65f)
                                .width(25.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            modifier = Modifier.clickable {
                                songViewModel.isDropdownMenuVisible.value = true
                            }, shape = RoundedCornerShape(8.dp), color = fieldBg
                        ) {
                            WeekdayDropDownMenu(
                                selectedDay = templateViewModel.tempWeekday,
                                modifier = Modifier.fillMaxWidth()
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
                    templateViewModel.tempGlorifyingSongs.value?.let {
                        AddNewSongToTemplate(
                            categoryTitle = "Փառաբանություն", categorySongs = it
                        ) {
                            selectedCategory.value = "Փառաբանություն"
                            selectedCategoryForAddNewSong = templateViewModel.tempGlorifyingSongs
                            selectedCategoryBottomSheetAllSongs =
                                bottomSheetAllSongsForGlorifyingCategory
                            scope.launch { bottomSheetState.show() }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))


                    templateViewModel.tempWorshipSongs.value?.let {
                        AddNewSongToTemplate(
                            categoryTitle = "Երկրպագություն", categorySongs = it
                        ) {
                            selectedCategory.value = "Երկրպագություն"
                            selectedCategoryForAddNewSong = templateViewModel.tempWorshipSongs
                            selectedCategoryBottomSheetAllSongs =
                                bottomSheetAllSongsForWorshipCategory
                            scope.launch { bottomSheetState.show() }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    templateViewModel.tempGiftSongs.value?.let {
                        AddNewSongToTemplate(
                            categoryTitle = "Ընծա", categorySongs = it
                        ) {
                            selectedCategory.value = "Ընծա"
                            selectedCategoryForAddNewSong = templateViewModel.tempGiftSongs
                            selectedCategoryBottomSheetAllSongs = bottomSheetAllSongsForGiftCategory
                            scope.launch { bottomSheetState.show() }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    SaveButton {
                        val checkFieldsState = templateViewModel.checkFields()
                        if (checkFieldsState.isSuccess) {
                            templateViewModel.saveSongTemplateToLocalStorage()
                        }
                        navController.popBackStack()
                        //                                Toast.makeText(LocalContext.current, "", Toast.LENGTH_SHORT).show()
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
                    onCloseClicked = { /*TODO*/ },
                    textSize = settingViewModel.songbookTextSize
                )
                selectedCategoryForAddNewSong.value?.let {

                    CategoryTabs(
                        categorySongs = categorySongsList.value,
                        categoryTitle = selectedCategory,
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

    BackHandler {
        navController.popBackStack()
    }
}

