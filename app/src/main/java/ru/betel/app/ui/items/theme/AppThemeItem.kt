package ru.betel.app.ui.items.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.domain.model.ui.ThemeMode

@Composable
fun AppThemeItem(mode: ThemeMode, modifier: Modifier, onItemClick: () -> Unit) {
    val appTheme = mode.mode
    Surface(
        elevation = 4.dp,
        modifier = modifier
            .padding(15.dp)
            .clickable {
                onItemClick()
            },
        shape = RoundedCornerShape(12.dp),
        color = appTheme.screenBackgroundColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(14.dp)
        ) {
            Column {
                val startSize = 70.dp
                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                        .background(appTheme.screenBackgroundColor)
                )
                Divider(
                    modifier = Modifier
                        .height(3.dp)
                        .width(startSize),
                    color = appTheme.primaryTextColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Divider(
                    modifier = Modifier
                        .height(3.dp)
                        .width(startSize - 20.dp),
                    color = appTheme.primaryTextColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Divider(
                    modifier = Modifier
                        .height(3.dp)
                        .width(startSize - 40.dp),
                    color = appTheme.primaryTextColor
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            RadioButton(
                selected = mode.isSelected.value,
                onClick = { onItemClick() },
                colors = RadioButtonDefaults.colors(selectedColor = appTheme.selectedBoxColor, unselectedColor = appTheme.unselectedBoxColor)
            )
        }
    }
}


@Composable
fun ThemeSelector(modes: List<ThemeMode>, settingViewModel: SettingViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        val theme1 = modes[0]
        val theme2 = modes[1]

        AppThemeItem(
            mode = theme1,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .fillMaxWidth(0.4f)
        ) {
            theme1.isSelected.value = true
            modes.forEach {
                it.isSelected.value = theme1 == it
            }
            settingViewModel.setTheme(0,theme1.mode)
        }

        AppThemeItem(
            mode = theme2,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .fillMaxWidth(0.4f)
        ) {
            theme2.isSelected.value = true
            modes.forEach {
                it.isSelected.value = theme2 == it
            }
            settingViewModel.setTheme(1,theme2.mode)
        }
    }
}