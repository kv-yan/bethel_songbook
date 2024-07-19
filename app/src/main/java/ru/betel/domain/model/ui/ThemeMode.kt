package ru.betel.domain.model.ui

import androidx.compose.ui.graphics.Color
import ru.betel.app.ui.theme.*
import ru.betel.app.ui.theme.textFieldPlaceholder

enum class ThemeMode(
    var isSelected: Boolean,
    val textColor: Color,
    val bgColor: Color,
) {
    Light(
        isSelected = true,
        textColor = textFieldPlaceholder,
        bgColor = Color.White,
    ),
    LightGray(
        isSelected = false,
        textColor = Color.Black,
        bgColor = Color.Gray,
    ),
    DarkGray(
        isSelected = false,
        textColor = Color(0xFFE8EEEE),
        bgColor = Color(0xFF2B3131),
    )
}



enum class AppTheme(
    val backgroundColor: Color,
    val actionBarIconColor: Color,
    val actionBarColor: Color,
    val actionStatusBarColor: Color,
    val dividerColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val fieldHIntTextColor: Color,
    val primaryIconColor: Color,
    val primaryButtonColor: Color,
    val fieldBackgroundColor: Color,
    val selectedBoxColor: Color,
    val unselectedBoxColor: Color,
    val screenBackgroundColor: Color,
    val drawerIconColor: Color,
    val drawerIconBackgroundColor: Color,
){
    PRIMARY(
        backgroundColor = primary_bgColor,
        actionBarIconColor = primary_actionbarIconColor,
        actionBarColor = primary_actionBarColor,
        actionStatusBarColor = primary_actionStatusBarColor,
        dividerColor = primary_dividerColor,
        primaryTextColor = primary_primaryTextColor,
        secondaryTextColor = primary_secondaryTextColor,
        fieldHIntTextColor = primary_fieldTextColor,
        primaryIconColor = primary_primaryIconColor,
        primaryButtonColor = primary_primaryButtonColor,
        fieldBackgroundColor = primary_secondaryButtonColor,
        selectedBoxColor = primary_selectedBoxColor,
        unselectedBoxColor = primary_unselectedBoxColor,
        screenBackgroundColor = primary_screenBackgroundColor,
        drawerIconColor = primary_drawerIconColor,
        drawerIconBackgroundColor = primary_drawerIconBackgroundColor
    ),

    DARK(
        backgroundColor = dark_bgColor,
        actionBarIconColor = dark_actionbarIconColor,
        actionBarColor = dark_actionBarColor,
        actionStatusBarColor = dark_actionStatusBarColor,
        dividerColor = dark_dividerColor,
        primaryTextColor = dark_primaryTextColor,
        secondaryTextColor = dark_secondaryTextColor,
        fieldHIntTextColor = dark_fieldTextColor,
        primaryIconColor = dark_primaryIconColor,
        primaryButtonColor = dark_primaryButtonColor,
        fieldBackgroundColor = dark_secondaryButtonColor,
        selectedBoxColor = dark_selectedBoxColor,
        unselectedBoxColor = dark_unselectedBoxColor,
        screenBackgroundColor = dark_screenBackgroundColor,
        drawerIconColor = dark_drawerIconColor,
        drawerIconBackgroundColor = dark_drawerIconBackgroundColor
    ),

    GRAY(
        backgroundColor = gray_bgColor,
        actionBarIconColor = gray_actionbarIconColor,
        actionBarColor = gray_actionBarColor,
        actionStatusBarColor = gray_actionStatusBarColor,
        dividerColor = gray_dividerColor,
        primaryTextColor = gray_primaryTextColor,
        secondaryTextColor = gray_secondaryTextColor,
        fieldHIntTextColor = gray_fieldTextColor,
        primaryIconColor = gray_primaryIconColor,
        primaryButtonColor = gray_primaryButtonColor,
        fieldBackgroundColor = gray_secondaryButtonColor,
        selectedBoxColor = gray_selectedBoxColor,
        unselectedBoxColor = gray_unselectedBoxColor,
        screenBackgroundColor = gray_screenBackgroundColor,
        drawerIconColor = gray_drawerIconColor,
        drawerIconBackgroundColor = gray_drawerIconBackgroundColor
    )
}
