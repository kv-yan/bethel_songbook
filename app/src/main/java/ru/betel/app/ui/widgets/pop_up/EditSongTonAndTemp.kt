package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.widgets.SaveButton
import ru.betel.app.ui.widgets.Temp
import ru.betel.app.ui.widgets.dropdown_menu.TonalityDropDownMenu
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.AppTheme

@Composable
fun EditSongTonAndTemp(
    isShowDialog: MutableState<Boolean>,
    songState: MutableState<Song>,
    appTheme: AppTheme,
    doAfterSave: (Song) -> Unit
) {
    val tonality = remember { mutableStateOf(songState.value.tonality) }
    val temp = remember { mutableFloatStateOf(songState.value.temp.toFloat()) }
    val isUsingSoundTrack = remember { mutableStateOf(songState.value.isUsingSoundTrack) }

    val onDismiss = { isShowDialog.value = false }

    val onSave = {
        val updatedSong = songState.value.copy(
            tonality = tonality.value,
            temp = temp.value.toInt().toString(),
            isUsingSoundTrack = isUsingSoundTrack.value
        )
        doAfterSave(updatedSong)
        onDismiss()
    }

    if (isShowDialog.value) {
        AlertDialog(shape = RoundedCornerShape(12.dp),
            backgroundColor = appTheme.screenBackgroundColor,
            onDismissRequest = { onDismiss() },
            buttons = {
                Surface(shape = RoundedCornerShape(15.dp), color = appTheme.screenBackgroundColor) {
                    Column(Modifier.padding(16.dp)) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Տոն*", style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 14.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                                fontWeight = FontWeight(400),
                                color = appTheme.secondaryTextColor,
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TonalityDropDownMenu(
                            appTheme = appTheme, tonality, modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                        Temp(temp = temp, appTheme = appTheme)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isUsingSoundTrack.value = !isUsingSoundTrack.value
                                }, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(colors = CheckboxDefaults.colors(
                                checkedColor = appTheme.primaryTextColor,
                                checkmarkColor = appTheme.fieldBackgroundColor,
                                uncheckedColor = appTheme.secondaryTextColor
                            ), checked = isUsingSoundTrack.value, onCheckedChange = {
                                isUsingSoundTrack.value = it
                            })
                            Text(text = "(Ֆ)", color = appTheme.primaryTextColor)
                        }

                        SaveButton(
                            appTheme = appTheme,
                        ) {
                            onSave()
                        }
                    }
                }
            })
    }
}
