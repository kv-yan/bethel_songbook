package ru.betel.app.ui.items.tonality

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.ui.theme.drawerLayoutSecondaryColor
import ru.betel.app.R
import ru.betel.app.ui.theme.textFieldPlaceholder
import ru.betel.domain.model.ui.SongTonality


@Composable
fun TonalityMenuItem(
    isShowDropdownMenu: MutableState<Boolean>,
    tonality: SongTonality,
    onClick: (item: SongTonality) -> Unit,
) {
    Box(Modifier
        .background(if (tonality.isSelected.value) drawerLayoutSecondaryColor else Color.White)
        .clickable {
            onClick(tonality)
            isShowDropdownMenu.value = false
        }
        .padding(vertical = 5.dp, horizontal = 11.dp)) {
        Text(
            text = tonality.ton, style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontFamily = FontFamily(Font(R.font.mardoto_medium)),
                fontWeight = FontWeight(400),
                color = if (tonality.isSelected.value) textFieldPlaceholder else Color.Black,
            ), textAlign = TextAlign.Center, modifier = Modifier.widthIn(min = 30.dp)
        )
    }
}