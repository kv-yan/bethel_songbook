package ru.betel.app.ui.items.weekday

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.drawerLayoutSecondaryColor
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.Weekday

@Composable
fun WeekdayMenuItem(
    appTheme: AppTheme,
    isShowDropdownMenu: MutableState<Boolean>,
    weekday: Weekday,
    onClick: (item: Weekday) -> Unit,
) {
    Box(Modifier
        .widthIn(min = 130.dp)
        .background(if (weekday.isSelected) appTheme.fieldBackgroundColor else appTheme.screenBackgroundColor)
        .clickable {
            onClick(weekday)
            isShowDropdownMenu.value = false
        }
        .padding(vertical = 5.dp, horizontal = 11.dp)) {
        Text(
            text = weekday.day,
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                fontWeight = FontWeight(400),
                color = if (weekday.isSelected) appTheme.primaryTextColor else appTheme.secondaryTextColor,
            ),
        )

    }
}