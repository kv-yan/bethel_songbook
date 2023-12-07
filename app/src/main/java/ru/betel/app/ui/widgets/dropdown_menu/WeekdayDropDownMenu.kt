package ru.betel.app.ui.widgets.dropdown_menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.items.weekday.WeekdayMenuItem
import ru.betel.app.ui.theme.fieldBg
import ru.betel.domain.model.ui.Weekday


@Composable
fun WeekdayDropDownMenu(selectedDay: MutableState<String>, modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) }
    val weekdays = remember {
        mutableStateOf(
            listOf(
                Weekday.Monday,
                Weekday.Tuesday,
                Weekday.Wednesday,
                Weekday.Thursday,
                Weekday.Friday,
                Weekday.Saturday,
                Weekday.Sunday
            )
        )
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = fieldBg,
        modifier = modifier.height(38.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded.value = true },
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = selectedDay.value,
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF111111),
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_drop_down),
                contentDescription = null,
                modifier = Modifier.size(width = 10.dp, height = 7.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            for (weekday in weekdays.value) {
                WeekdayMenuItem(isShowDropdownMenu = expanded, weekday = weekday) { item ->
                    selectedDay.value = item.day
                    weekdays.value.forEach {
                        it.isSelected = item.day == it.day

                    }
                    weekdays.value = weekdays.value
                }

            }
        }
    }
}

