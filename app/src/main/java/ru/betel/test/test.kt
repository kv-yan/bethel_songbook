package ru.betel.test

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.widgets.LazyColumnForAddNewTemplate
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.dropdown_menu.WeekdayDropDownMenu
import ru.betel.app.ui.widgets.pop_up.EditSongTonAndTemp
import ru.betel.domain.model.Song

@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun TestStyle() {
//    SongHorizontalPager()
//    NewTemplate()
    AddNewTemplateCategoryItem()
}

@Composable
fun NewTemplate() {
    val performerText = remember { mutableStateOf("") }
    val weekdayText = remember { mutableStateOf("Կիրակի") }
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MyTextFields(
                placeholder = "Performer",
                fieldText = performerText,
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.width(6.dp))

            WeekdayDropDownMenu(selectedDay = weekdayText, Modifier.fillMaxWidth())
        }

        // divider
        Surface(
            Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFEDEDED))
        ) {

        }


        // categories part
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AddNewTemplateCategoryItem() {
    val isShowEditTonalityTempDialog = remember {
        mutableStateOf(false)
    }
    val selectedSong = remember {
        mutableStateOf(
            Song(
                id = "Error",
                title = "Error",
                tonality = "Error",
                words = "Error",
                isGlorifyingSong = false,
                isWorshipSong = false,
                isGiftSong = false,
                isFromSongbookSong = false,
                temp = "Error"
            )
        )
    }

    Surface(
        color = fieldBg,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.padding(start = 10.dp ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Category", style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF111111),
                    ), modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
                IconButton(onClick = {  }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_new_song_item),
                        contentDescription = "add song to category",
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(start = 10.dp , end = 10.dp , bottom = if (testSongList.isNotEmpty()) 0.dp else 12.dp)
            ) {
                LazyColumnForAddNewTemplate(songList = testSongList) {
                    selectedSong.value = it
                    isShowEditTonalityTempDialog.value = true
                }
            }
        }

        if (isShowEditTonalityTempDialog.value) {
            EditSongTonAndTemp(isShowEditTonalityTempDialog, mutableSongState = selectedSong)
        }
    }
}