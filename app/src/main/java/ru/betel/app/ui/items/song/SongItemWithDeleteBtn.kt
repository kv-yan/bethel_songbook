package ru.betel.app.ui.items.song

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.domain.model.Song


@Composable
fun SongItemWithDeleteBtn(
    item: Song, index: Int,
   /* isLastItem: MutableState<Boolean>,*/
    onDeleteItemClick: (item: Song) -> Unit,
    onTonalityTempItemLongPres: (item: Song) -> Unit,
) {
    val draggableState = rememberDraggableState {}
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 35.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$index.", style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color.Black
                ), modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = item.title, maxLines = 1, style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color.Black
                ), modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(start = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${item.temp} | ${item.tonality}", style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                ), modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onLongPress = { onTonalityTempItemLongPres(item) })
                })

                Spacer(modifier = Modifier.width(12.dp))
                Icon(painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete this song",
                    Modifier
                        .size(12.dp)
                        .clickable {
                            onDeleteItemClick(item)
                        })
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        Spacer(
            Modifier.height(8.dp)
        )
//        if (!isLastItem.value) {
            Divider(Modifier.fillMaxWidth(), 1.dp, color = songDividerColor)
//        }
    }
}