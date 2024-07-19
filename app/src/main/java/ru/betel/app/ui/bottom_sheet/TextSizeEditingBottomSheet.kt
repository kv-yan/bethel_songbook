package ru.betel.app.ui.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.ui.items.theme.AppThemeItem
import ru.betel.app.ui.widgets.TextBtnForChangeTextSize
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.domain.model.ui.ThemeMode

@Composable
fun TextSizeEditingBottomSheetContent(
    modes: SnapshotStateList<ThemeMode>,
    settingViewModel: SettingViewModel,
) {

    Surface(shape = RoundedCornerShape(12.dp), color = Color.White) {
        Column {
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Փոխեք տառաչափը։", modifier = Modifier.padding(start = 36.dp))
            Spacer(modifier = Modifier.height(12.dp))
            TextSizeEditingBottomSheetBlock(onMinusBtnClick = { settingViewModel.decreaseTextSize() },
                onPlusBtnClick = { settingViewModel.increaseTextSize() })
            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
            ) {
                items(modes) { mode ->
                    AppThemeItem(mode) {
                        mode.isSelected = true
                        modes.forEach {
                            it.isSelected = mode == it
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TextSizeEditingBottomSheetBlock(onPlusBtnClick: () -> Unit, onMinusBtnClick: () -> Unit) {
    Surface {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextBtnForChangeTextSize(
                    Modifier
                        .fillMaxWidth(0.4f)
                        .clickable {
                            onMinusBtnClick()
                        }) {
                    Text(
                        text = "Ա", style = TextStyle.Default.copy(
                            fontSize = 12.sp, textAlign = TextAlign.Center, color = Color.White
                        ), textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                TextBtnForChangeTextSize(
                    Modifier
                        .fillMaxWidth(0.7f)
                        .clickable { onPlusBtnClick() }) {
                    Text(
                        text = "Ա", style = TextStyle.Default.copy(
                            fontSize = 22.sp, textAlign = TextAlign.Center, color = Color.White
                        )
                    )
                }
            }
        }
    }
}


