package ru.betel.app.ui.screens.new_song

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.dropdown_menu.CategoryDropDownMenuWithCheckBox
import ru.betel.app.ui.widgets.dropdown_menu.TonalityDropDownMenu
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.SongsCategory

private const val TAG = "HomeScreen"

@Composable
fun NewSongScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    settingViewModel: SettingViewModel,
    songViewModel: SongViewModel
) {
    actionBarState.value = ActionBarState.NEW_SONG_SCREEN
    val categoryTextFieldValue = remember { mutableStateOf("") }
    val tonalityTextFieldValue = remember { mutableStateOf("") }
    val tempTextFieldValue = remember { mutableStateOf("") }
    val songTitleTextFieldValue = remember { mutableStateOf("") }
    val songWordsTextFieldValue = remember { mutableStateOf("") }


    val selectedCategory = remember { mutableStateOf("") }
    val isGlorifying = remember { mutableStateOf(false) }
    val isWorship = remember { mutableStateOf(false) }
    val isGift = remember { mutableStateOf(false) }
    val isFromSongbook = remember { mutableStateOf(false) }
    val selectedItemListOf = remember {
        mutableListOf(isGlorifying, isWorship, isGift, isFromSongbook)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    selectedCategory = selectedCategory, categories = listOf(
                        SongsCategory.GLORIFYING,
                        SongsCategory.WORSHIP,
                        SongsCategory.GIFT,
                        SongsCategory.FROM_SONGBOOK
                    ), categoryStates = selectedItemListOf, modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                TonalityDropDownMenu(tonalityTextFieldValue, modifier = Modifier.fillMaxSize(0.5f))
                Spacer(modifier = Modifier.width(6.dp))
                MyTextFields(
                    placeholder = "Տեմպ",
                    imeAction = ImeAction.Next,
                    fieldText = tempTextFieldValue,
                    modifier = Modifier.fillMaxWidth(),
                    textType = KeyboardType.Number
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = songDividerColor)
        )
        Spacer(modifier = Modifier.height(12.dp))

        MyTextFields(
            placeholder = "Վերնագիր",
            fieldText = songTitleTextFieldValue,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .width(40.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MyTextFields(
            placeholder = "Տեքստ",
            fieldText = songWordsTextFieldValue,
            align = Alignment.Top,
            imeAction = ImeAction.Default,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 210.dp, max = Dp.Infinity)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = songDividerColor)
        )
        SaveButton {
            val currentSong = Song(
                "",
                title = songTitleTextFieldValue.value,
                tonality = tonalityTextFieldValue.value,/* temp =*/
                words = songWordsTextFieldValue.value,
                isGlorifyingSong = isGlorifying.value,
                isWorshipSong = isWorship.value,
                isGiftSong = isGift.value,
                isFromSongbookSong = isFromSongbook.value
            )

            songViewModel.saveSongToFirebase(currentSong)
//            navController.navigate(Screens.HOME_SCREEN.route)
        }
    }
}