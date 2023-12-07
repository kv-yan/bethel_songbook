package ru.betel.app.ui.items.song

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.ui.theme.drawerLayoutSecondaryColor
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.data.extensions.getWordsFirst2Lines
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AddSong
import ru.betel.domain.model.ui.SongbookTextSize


@Composable
fun SongItemWithWords(item: Song, textSize: SongbookTextSize, onItemClick: () -> Unit) {
    val isShowAdditionBtn = remember { mutableStateOf(false) }
    val backgroundColor =
        remember { mutableStateOf(if (isShowAdditionBtn.value) drawerLayoutSecondaryColor else Color.White) }

    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onLongPress = {
                isShowAdditionBtn.value = !isShowAdditionBtn.value
                backgroundColor.value =
                    if (isShowAdditionBtn.value) drawerLayoutSecondaryColor else Color.White
            }, onTap = {
                onItemClick()
            })
        }
        .background(color = backgroundColor.value)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = item.title, style = TextStyle(
                        fontSize = textSize.normalItemDefaultTextSize,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF111111),
                    )
                )
                Text(
                    text = item.getWordsFirst2Lines(), style = TextStyle(
                        fontSize = textSize.smallItemDefaultTextSize,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(400),
                        color = Color.Black.copy(alpha = 0.5f)
                    ), modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            AnimatedVisibility(
                visible = isShowAdditionBtn.value,
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        rememberScrollState()
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(end = 12.dp)
                ) {

                    IconButton(onClick = { }, modifier = Modifier.size(20.dp, 20.dp)) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp, 20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(onClick = { }, modifier = Modifier.size(20.dp, 20.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp, 20.dp)
                        )

                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(onClick = { }, modifier = Modifier.size(20.dp, 20.dp)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(17.dp)
                        )

                    }

                }
            }
        }
        Divider(
            color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun SongItemWithWordsAndCheckBox(
    item: AddSong,
    onItemLongPress: (Song) -> Unit,
    onItemClick: () -> Unit,
) {
    val isShowAdditionBtn = remember { mutableStateOf(false) }
    val backgroundColor =
        remember { mutableStateOf(if (isShowAdditionBtn.value) drawerLayoutSecondaryColor else Color.White) }

    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onLongPress = {
                onItemLongPress(item.song)
            }, onTap = {
                onItemClick()
            })
        }
        .background(color = backgroundColor.value)
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            RadioButton(selected = item.isAdded.value, onClick = { onItemClick() }, colors = RadioButtonDefaults.colors(selectedColor = actionBarColor))
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.song.title, style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF111111),
                    )
                )
                Text(
                    text = item.song.getWordsFirst2Lines(), style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                        fontWeight = FontWeight(400),
                        color = Color.Black.copy(alpha = 0.5f)
                    ), modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            AnimatedVisibility(
                visible = isShowAdditionBtn.value,
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        rememberScrollState()
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(end = 12.dp)
                ) {

                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp, 20.dp)
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp, 20.dp)
                        )

                    }
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp, 20.dp)
                        )

                    }

                }
            }
        }
        Divider(
            color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp)
        )
    }
}
