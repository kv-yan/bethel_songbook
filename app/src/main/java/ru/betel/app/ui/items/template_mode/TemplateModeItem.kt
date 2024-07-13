package ru.betel.app.ui.items.template_mode

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
import ru.betel.app.ui.theme.drawerLayoutSecondaryColor
import ru.betel.app.R
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.app.ui.widgets.dropdown_menu.TemplateModeType
import ru.betel.domain.model.ui.Weekday

@Composable
fun TemplateModeItem(
    isShowDropdownMenu: MutableState<Boolean>,
    isSelected:Boolean,
    item: TemplateModeType,
    onClick: (TemplateModeType) -> Unit,
) {
    Box(
        Modifier
            .widthIn(min = 130.dp)
            .background(if (isSelected) drawerLayoutSecondaryColor else Color.White)
            .clickable {
                onClick(item)
                isShowDropdownMenu.value = false
            }
            .padding(vertical = 5.dp, horizontal = 11.dp)
    ) {
        Text(
            text = item.title,
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                fontWeight = FontWeight(400),
                color = if (isSelected) textFieldPlaceholder else Color.Black,
            ),
        )

    }
}