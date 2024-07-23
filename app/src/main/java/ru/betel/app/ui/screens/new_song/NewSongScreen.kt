package ru.betel.app.ui.screens.new_song

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.betel.app.R
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.dropdown_menu.CategoryDropDownMenuWithCheckBox
import ru.betel.app.ui.widgets.dropdown_menu.TonalityDropDownMenu
import ru.betel.app.ui.widgets.snackbar.AppSnackbar
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.ActionBarState
import ru.betel.domain.model.ui.NewSongFieldState
import ru.betel.domain.model.ui.Screens
import ru.betel.domain.model.ui.SongsCategory

private const val TAG = "HomeScreen"


@Composable
fun NewSongScreen(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    settingViewModel: SettingViewModel,
    songViewModel: SongViewModel
) {
    val newSongFieldState = remember { mutableStateOf(NewSongFieldState.INVALID_CATEGORY) }
    val isShowingSaveDialog = remember { mutableStateOf(false) }
    val appTheme = settingViewModel.appTheme.value

    Box(modifier = Modifier.fillMaxSize()) {
        MainContent(
            navController,
            actionBarState,
            settingViewModel,
            songViewModel,
            isShowingSaveDialog,
            newSongFieldState
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
                    .background(appTheme.primaryButtonColor, RoundedCornerShape(8.dp))
                    .padding(16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(if (newSongFieldState.value == NewSongFieldState.DONE) R.drawable.ic_done else R.drawable.ic_error),
                    contentDescription = null,
                    tint = appTheme.actionBarIconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = newSongFieldState.value.msg,
                    style = MaterialTheme.typography.subtitle1,
                    color = appTheme.actionBarIconColor,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }

    }
}

@Composable
private fun MainContent(
    navController: NavController,
    actionBarState: MutableState<ActionBarState>,
    settingViewModel: SettingViewModel,
    songViewModel: SongViewModel,
    isShowingSaveDialog: MutableState<Boolean>,
    newSongFieldState: MutableState<NewSongFieldState>,

    ) {

    val appTheme = settingViewModel.appTheme.value
    actionBarState.value = ActionBarState.NEW_SONG_SCREEN
    val songTonality = remember { mutableStateOf("") }
    val songTemp = remember { mutableStateOf("") }
    val songTitle = remember { mutableStateOf("") }
    val songWords = remember { mutableStateOf("") }


    val isGlorifying = remember { mutableStateOf(false) }
    val isWorship = remember { mutableStateOf(false) }
    val isGift = remember { mutableStateOf(false) }
    val isFromSongbook = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf("") }
    val selectedItemListOf = remember {
        mutableListOf(isGlorifying, isWorship, isGift, isFromSongbook)
    }

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
                    appTheme = appTheme, selectedCategory = selectedCategory, categories = listOf(
                        SongsCategory.GLORIFYING,
                        SongsCategory.WORSHIP,
                        SongsCategory.GIFT,
                        SongsCategory.FROM_SONGBOOK
                    ), categoryStates = selectedItemListOf, modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.height(38.dp)) {
                TonalityDropDownMenu(
                    appTheme = appTheme, songTonality, modifier = Modifier.fillMaxWidth(0.5f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                MyTextFields(
                    appTheme = appTheme,
                    placeholder = "Տեմպ",
                    imeAction = ImeAction.Next,
                    fieldText = songTemp,

                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
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
            appTheme = appTheme,
            placeholder = "Վերնագիր",
            fieldText = songTitle,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .width(40.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MyTextFields(
            appTheme = appTheme,
            placeholder = "Տեքստ",
            fieldText = songWords,
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
                .background(color = appTheme.dividerColor)
        )
        SaveButton(appTheme = appTheme) {
            val currentSong = Song(
                "",
                title = songTitle.value,
                tonality = songTonality.value,
                words = songWords.value,
                temp = songTemp.value,
                isGlorifyingSong = isGlorifying.value,
                isWorshipSong = isWorship.value,
                isGiftSong = isGift.value,
                isFromSongbookSong = isFromSongbook.value
            )
            savingLogic(currentSong, isShowingSaveDialog, newSongFieldState, onCompleted = {
                cleanFieldsValues(
                    songTitle = songTitle,
                    songTonality = songTonality,
                    songWords = songWords,
                    songTemp = songTemp,
                    songIsGlorifyingSong = isWorship,
                    songIsWorshipSong = isGift,
                    songIsGiftSong = isFromSongbook,
                    songIsFromSongbookSong = isGlorifying
                )
            }, onSave = {
                songViewModel.saveSongToFirebase(currentSong)
                navController.navigate(Screens.HOME_SCREEN.route)
            })
        }
    }
}

fun savingLogic(
    newSong: Song,
    showDialog: MutableState<Boolean>,
    newSongFieldState: MutableState<NewSongFieldState>,
    onCompleted: () -> Unit,
    onSave: () -> Unit,
) {
    if (newSong.title.isEmpty()) {
        newSongFieldState.value = NewSongFieldState.INVALID_TITLE
        Log.e(TAG, "savingLogic: INVALID_TITLE")
    } else if (newSong.words.isEmpty()) {
        newSongFieldState.value = NewSongFieldState.INVALID_WORDS
        Log.e(TAG, "savingLogic: INVALID_WORDS")
    } else if (newSong.tonality.isEmpty()) {
        newSongFieldState.value = NewSongFieldState.INVALID_TONALITY
        Log.e(TAG, "savingLogic: INVALID_TONALITY")
    } else if ((newSong.temp.isEmpty()) || newSong.temp.toInt() == 0 || newSong.temp.toInt() < 0) {
        newSongFieldState.value = NewSongFieldState.INVALID_TEMP
        Log.e(TAG, "savingLogic: INVALID_TEMP")
    } else if (!newSong.isGlorifyingSong && !newSong.isWorshipSong) {
        newSongFieldState.value = NewSongFieldState.INVALID_CATEGORY
        Log.e(TAG, "savingLogic: INVALID_CATEGORY")
    } else {
        onSave()
        onCompleted.invoke()
        newSongFieldState.value = NewSongFieldState.DONE
        Log.e(TAG, "savingLogic: DONE")
    }
    showDialog.value = true
}

fun cleanFieldsValues(
    songTitle: MutableState<String>,
    songTonality: MutableState<String>,
    songWords: MutableState<String>,
    songTemp: MutableState<String>,
    songIsGlorifyingSong: MutableState<Boolean>,
    songIsWorshipSong: MutableState<Boolean>,
    songIsGiftSong: MutableState<Boolean>,
    songIsFromSongbookSong: MutableState<Boolean>,
) {
    songTitle.value = ""
    songTonality.value = ""
    songWords.value = ""
    songTemp.value = ""

    songIsGlorifyingSong.value = false
    songIsWorshipSong.value = false
    songIsGiftSong.value = false
    songIsFromSongbookSong.value = false
}
