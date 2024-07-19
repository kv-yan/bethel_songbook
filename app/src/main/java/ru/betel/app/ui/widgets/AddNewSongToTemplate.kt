package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.widgets.pop_up.EditSongTonAndTemp
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AppTheme

@Composable
fun AddNewSongToTemplate(
    appTheme: AppTheme,
    categoryTitle: String,
    categorySongs: SnapshotStateList<Song>?,
    onAddItemClick: () -> Unit,
) {
    val isShowEditTonalityTempDialog = remember { mutableStateOf(false) }
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
        color = appTheme.fieldBackgroundColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryTitle, style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(400),
                        color = appTheme.secondaryTextColor,
                    ), modifier = Modifier.fillMaxWidth(0.9f)
                )
                IconButton(onClick = { onAddItemClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_new_song_item),
                        contentDescription = "add song to category",
                        tint = appTheme.secondaryTextColor,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
            Surface(
                color = appTheme.screenBackgroundColor, shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(
                    start = 10.dp,
                    end = 10.dp,
                    bottom = if (categorySongs?.isEmpty() == true) 0.dp else 12.dp
                )
            ) {
                if (categorySongs != null) {
                    LazyColumnForAddNewTemplate(songList = categorySongs, appTheme=appTheme) {
                        selectedSong.value = it
                        isShowEditTonalityTempDialog.value = true
                    }
                }
            }
        }

        if (isShowEditTonalityTempDialog.value) {
            EditSongTonAndTemp(isShowEditTonalityTempDialog, mutableSongState = selectedSong)
        }
    }
}

