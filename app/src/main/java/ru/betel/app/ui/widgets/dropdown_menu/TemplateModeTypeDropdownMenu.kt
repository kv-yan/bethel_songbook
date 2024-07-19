package ru.betel.app.ui.widgets.dropdown_menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.items.template_mode.TemplateModeItem
import ru.betel.domain.model.ui.AppTheme

enum class TemplateModeType(val title: String) {
    Categorised("Կարգավորված"), SingleMode("Անկարգ")
}

@Composable
fun TemplateModeTypeDropdownMenu(isSingleMode: MutableState<Boolean>, appTheme: AppTheme) {
    val expanded = remember { mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = appTheme.actionBarColor,
        modifier = Modifier.height(38.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { expanded.value = true },
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 6.dp),
                text = if (isSingleMode.value) TemplateModeType.SingleMode.title else TemplateModeType.Categorised.title,
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                    fontWeight = FontWeight(400),
                    color = appTheme.actionBarIconColor,
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_drop_down),
                contentDescription = null,
                tint = appTheme.actionBarIconColor,
                modifier = Modifier.size(width = 10.dp, height = 7.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            TemplateModeItem(
                isShowDropdownMenu = expanded,
                isSelected = !isSingleMode.value,
                item = TemplateModeType.Categorised
            ) {
                isSingleMode.value = false
            }
            TemplateModeItem(
                isShowDropdownMenu = expanded,
                isSelected = isSingleMode.value,
                item = TemplateModeType.SingleMode
            ) {
                isSingleMode.value = true
            }
        }
    }
}
