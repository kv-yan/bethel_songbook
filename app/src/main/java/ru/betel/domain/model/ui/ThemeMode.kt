package ru.betel.domain.model.ui

import androidx.compose.ui.graphics.Color
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.theme.textFieldPlaceholder

enum class ThemeMode(
    var isSelected: Boolean,
    val textColor: Color,
    val bgColor: Color,
    val iconsColor: Color,
) {
    Light(
        isSelected = true,
        textColor = textFieldPlaceholder,
        bgColor = Color.White,
        iconsColor = actionBarColor
    ),
    LightGray(
        isSelected = false,
        textColor = Color.Black,
        bgColor = Color.Gray,
        iconsColor = Color.Black
    ),
    DarkGray(
        isSelected = false,
        textColor = Color(0xFFE8EEEE),
        bgColor = Color(0xFF2B3131),
        iconsColor = Color(0xFFE2E2E2)
    )
}