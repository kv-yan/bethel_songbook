package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.domain.model.Song

@Composable
fun SeeSongDialog(song: Song, isShowDialog: MutableState<Boolean>) {
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(key1 =song ){
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    val onDismiss = { isShowDialog.value = !isShowDialog.value }
    if (isShowDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() },
            modifier = Modifier.padding(vertical = 80.dp),
            buttons = {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val verticalScrollState = rememberScrollState()
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .verticalScroll(verticalScrollState),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = song.title,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                    fontWeight = FontWeight(700),
                                    color = actionBarColor,
                                ),
                                textAlign = TextAlign.Start, /*modifier = Modifier.fillMaxWidth(0.8f)*/
                            )

                            Text(
                                text = "${song.temp} / ${song.tonality}",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                    fontWeight = FontWeight(700),
                                    color = textFieldPlaceholder,
                                ),
                                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End,
                            )

                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = song.words,
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 14.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(400),
                                color = Color.Black.copy(alpha = 0.7f)
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                                .fillMaxSize()
                        )
                    }
                }
            })
    }
}
