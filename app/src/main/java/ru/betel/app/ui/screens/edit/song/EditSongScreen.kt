package ru.betel.app.ui.screens.edit.song

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.TempElement
import ru.betel.app.ui.widgets.dropdown_menu.CategoryDropDownMenuWithCheckBox
import ru.betel.app.ui.widgets.dropdown_menu.TonalityDropDownMenu
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.Screens
import ru.betel.domain.model.ui.SongsCategory

private const val TAG = "EditSongScreen"


@Composable
fun EditSongScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    settingViewModel: SettingViewModel,
    templateViewModel: TemplateViewModel,
    editViewModel: EditViewModel,
    songViewModel: SongViewModel
) {
    val appTheme = settingViewModel.appTheme.value

    val currentSong by editViewModel.currentSong.collectAsState()
    actionBarState.value = ActionBarState.NEW_SONG_SCREEN

    val isEditingSongFromTemplate = editViewModel.isEditingSongFromTemplate
    val templateId = if (isEditingSongFromTemplate.value) {
        templateViewModel.singleTemplate.value
    } else null

    val allSongList = songViewModel.allSongState.collectAsState(initial = mutableListOf())

    val tonality = remember { mutableStateOf(currentSong.tonality) }
    val temp = remember { mutableFloatStateOf(currentSong.temp.toInt().toFloat()) }
    val title = remember { mutableStateOf(currentSong.title) }
    val words = remember { mutableStateOf(currentSong.words) }


    val selectedCategory = remember { mutableStateOf("") }
    val isGlorifying = remember { mutableStateOf(currentSong.isGlorifyingSong) }
    val isWorship = remember { mutableStateOf(currentSong.isWorshipSong) }
    val isGift = remember { mutableStateOf(currentSong.isGiftSong) }
    val isFromSongbook = remember { mutableStateOf(currentSong.isFromSongbookSong) }
    val selectedItemListOf = remember { listOf(isGlorifying, isWorship, isGift, isFromSongbook) }


    Column(
        Modifier
            .fillMaxSize()
            .background(appTheme.screenBackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CategoryDropDownMenuWithCheckBox(
                    selectedCategory = selectedCategory,
                    categories = listOf(
                        SongsCategory.GLORIFYING,
                        SongsCategory.WORSHIP,
                        SongsCategory.GIFT,
                        SongsCategory.FROM_SONGBOOK
                    ),
                    categoryStates = selectedItemListOf,
                    modifier = Modifier,
                    appTheme = settingViewModel.appTheme.value
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                TonalityDropDownMenu(
                    appTheme = appTheme, tonality, modifier = Modifier.fillMaxSize(0.5f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                TempElement(temp = temp, appTheme = appTheme)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Divider(modifier = Modifier.fillMaxWidth(), color = appTheme.dividerColor, thickness = 1.dp)

        Spacer(modifier = Modifier.height(12.dp))

        MyTextFields(
            appTheme = appTheme,
            placeholder = "Վերնագիր",
            fieldText = title,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .width(40.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MyTextFields(
            appTheme = appTheme,
            placeholder = "Տեքստ",
            fieldText = words,
            imeAction = ImeAction.Default,
            align = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
                .heightIn(min = 0.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Divider(modifier = Modifier.fillMaxWidth(), color = appTheme.dividerColor, thickness = 1.dp)

        SaveButton(appTheme = appTheme) {
            val updatedSong = currentSong.copy(
                title = title.value,
                tonality = tonality.value,
                words = words.value,
                temp = temp.value.toInt().toString(),
                isGlorifyingSong = isGlorifying.value,
                isWorshipSong = isWorship.value,
                isGiftSong = isGift.value,
                isFromSongbookSong = isFromSongbook.value
            )

            editViewModel.onSaveUpdates(
                current = editViewModel.currentSong.value,
                updatedSong = updatedSong,
                isFromTemplate = isEditingSongFromTemplate.value,
                template = templateId,
                allSongList = allSongList.value
            )

            /*
            editViewModel.onSaveUpdates(
                editViewModel.currentSong.value,
                updatedSong,
                isEditingSongFromTemplate.value,
                allSongList.value
            )*/

            Log.e(TAG, "EditSongScreen: song :: $updatedSong")
            navController.navigate(Screens.HOME_SCREEN.route)
        }
    }
}