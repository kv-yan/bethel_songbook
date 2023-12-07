package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.widgets.pop_up.EditSongTonAndTemp
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.SongbookTextSize

@Composable
fun CategorySongs(
    categoryTitle: String,
    fontSize: SongbookTextSize,
    categorySongs: List<Song>,
    onItemClick: (Song) -> Unit,
) {
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
            )
        )
    }

    Surface(
        color = fieldBg,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = categoryTitle,
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF111111),
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
            )
            Surface(
                color = Color.White, shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(
                    start = 10.dp,
                    end = 10.dp,
                    bottom = if (categorySongs.isEmpty()) 0.dp else 12.dp
                )
            ) {
                LazyColumnForCategorySongs(songList = categorySongs, fontSize = fontSize) {
                    onItemClick(it)
                }
            }
        }

        if (isShowEditTonalityTempDialog.value) {
            EditSongTonAndTemp(isShowEditTonalityTempDialog, mutableSongState = selectedSong)
        }
    }
}
