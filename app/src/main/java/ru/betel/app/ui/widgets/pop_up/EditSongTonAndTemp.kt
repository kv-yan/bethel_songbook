package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.fieldBg
import ru.betel.app.ui.theme.songDividerColor
import ru.betel.app.ui.widgets.MyTextFields
import ru.betel.app.ui.widgets.MyTextFieldsForEditScreen
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.domain.model.Song

@Composable
fun EditSongTonAndTemp(
    isShowDialog: MutableState<Boolean>,
    mutableSongState: MutableState<Song>,
) {
    val tonality = remember {
        mutableStateOf(mutableSongState.value.tonality)
    }
    println()
    val temp = remember {
        mutableStateOf(mutableSongState.value.temp)
    }
    val onDismiss = { isShowDialog.value = !isShowDialog.value }
    if (isShowDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, buttons = {
            Surface(shape = RoundedCornerShape(15.dp), color = Color.White) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(0.9f), horizontalArrangement = Arrangement.Center) {
                        // tonality
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(Modifier.fillMaxWidth(0.5f)) {
                            Text(
                                text = "Տոն*", style = TextStyle(
                                    fontSize = 13.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF756F86),
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            MyTextFieldsForEditScreen(
                                placeholder = "Տոն",
                                fieldText = tonality,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // tonality
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                text = "Տեմպ*", style = TextStyle(
                                    fontSize = 13.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF756F86),
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            MyTextFieldsForEditScreen(
                                placeholder = "Տեմպ",
                                fieldText = tonality,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(Modifier.fillMaxWidth(), 1.dp, color = songDividerColor)

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(0.9f)) {
                            SaveButton(modifier = Modifier.fillMaxWidth(0.5f), fontSize = 13.sp) {
                                mutableSongState.value.tonality = tonality.value
//                                mutableSongState.value.temp = temp

                                isShowDialog.value = false
                            }

                            SaveButton(
                                text = "Չեղարկել",
                                btnColor = fieldBg,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 13.sp
                            ) {
                                isShowDialog.value = false
                            }
                        }
                    }

                }
            }

        })
    }
}

@Preview
@Composable
fun Dialog() {
    val isShowing = remember {
        mutableStateOf(true)
    }
    val testSong = remember {
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
    EditSongTonAndTemp(isShowDialog = isShowing, mutableSongState = testSong)
}