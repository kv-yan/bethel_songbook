package ru.betel.app.ui.items.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.domain.model.ui.ThemeMode

@Composable
fun AppThemeItem(mode: ThemeMode, onItemClick: () -> Unit) {
    Surface(
        elevation = 2.dp, modifier = Modifier
            .padding(15.dp)
            .clickable {
                onItemClick()
            }, shape = RoundedCornerShape(12.dp), color = mode.bgColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(14.dp)
        ) {
            Column {
                val startSize = 70.dp
                Spacer(modifier = Modifier
                    .height(15.dp)
                    .background(mode.bgColor))
                Divider(
                    modifier = Modifier
                        .height(3.dp)
                        .width(startSize), color = mode.textColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Divider(
                    modifier = Modifier
                        .height(3.dp)
                        .width(startSize - 20.dp),
                    color = mode.textColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Divider(
                    modifier = Modifier
                        .height(3.dp)
                        .width(startSize - 40.dp),
                    color = mode.textColor
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            RadioButton(
                selected = mode.isSelected,
                onClick = { onItemClick() },
                colors = RadioButtonDefaults.colors(selectedColor = actionBarColor)
            )
        }
    }
}


@Preview
@Composable
fun AppThemeItemPrev() {
    AppThemeItem(ThemeMode.LightGray){}
}