package ru.betel.domain.model.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import ru.betel.app.ui.theme.gray_actionBarColor
import ru.betel.app.ui.theme.gray_actionStatusBarColor
import ru.betel.app.ui.theme.gray_actionbarIconColor
import ru.betel.app.ui.theme.gray_bgColor
import ru.betel.app.ui.theme.gray_dividerColor
import ru.betel.app.ui.theme.gray_drawerFieldSelectedColor
import ru.betel.app.ui.theme.gray_drawerFieldUnselectedColor
import ru.betel.app.ui.theme.gray_drawerSelectedIconColor
import ru.betel.app.ui.theme.gray_drawerUnselectedIconColor
import ru.betel.app.ui.theme.gray_fieldTextColor
import ru.betel.app.ui.theme.gray_primaryButtonColor
import ru.betel.app.ui.theme.gray_primaryIconColor
import ru.betel.app.ui.theme.gray_primaryTextColor
import ru.betel.app.ui.theme.gray_screenBackgroundColor
import ru.betel.app.ui.theme.gray_secondaryButtonColor
import ru.betel.app.ui.theme.gray_secondaryTextColor
import ru.betel.app.ui.theme.gray_selectedBoxColor
import ru.betel.app.ui.theme.gray_unselectedBoxColor
import ru.betel.app.ui.theme.primary_actionBarColor
import ru.betel.app.ui.theme.primary_actionStatusBarColor
import ru.betel.app.ui.theme.primary_actionbarIconColor
import ru.betel.app.ui.theme.primary_bgColor
import ru.betel.app.ui.theme.primary_dividerColor
import ru.betel.app.ui.theme.primary_drawerFieldSelectedColor
import ru.betel.app.ui.theme.primary_drawerFieldUnselectedColor
import ru.betel.app.ui.theme.primary_drawerSelectedIconColor
import ru.betel.app.ui.theme.primary_drawerUnselectedIconColor
import ru.betel.app.ui.theme.primary_fieldTextColor
import ru.betel.app.ui.theme.primary_primaryButtonColor
import ru.betel.app.ui.theme.primary_primaryIconColor
import ru.betel.app.ui.theme.primary_primaryTextColor
import ru.betel.app.ui.theme.primary_screenBackgroundColor
import ru.betel.app.ui.theme.primary_secondaryButtonColor
import ru.betel.app.ui.theme.primary_secondaryTextColor
import ru.betel.app.ui.theme.primary_selectedBoxColor
import ru.betel.app.ui.theme.primary_unselectedBoxColor

enum class ThemeMode(
    var isSelected: MutableState<Boolean>,
    val textColor: Color,
    val bgColor: Color,
    val mode: AppTheme,
) {
    Light(
        isSelected = mutableStateOf(true),
        textColor = AppTheme.PRIMARY.primaryTextColor,
        bgColor = AppTheme.PRIMARY.screenBackgroundColor,
        mode = AppTheme.PRIMARY
    ),
    Dark(
        isSelected = mutableStateOf(false),
        textColor = AppTheme.GRAY.primaryTextColor,
        bgColor = AppTheme.GRAY.screenBackgroundColor,
        mode = AppTheme.GRAY
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
    val drawerSelectedIconColor: Color,
    val drawerUnselectedIconColor: Color,
    val drawerFieldSelectedColor: Color,
    val drawerFieldUnselectedColor: Color,

    ) {
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
        drawerSelectedIconColor = primary_drawerSelectedIconColor,
        drawerUnselectedIconColor = primary_drawerUnselectedIconColor,
        drawerFieldSelectedColor = primary_drawerFieldSelectedColor ,
        drawerFieldUnselectedColor = primary_drawerFieldUnselectedColor
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
        drawerSelectedIconColor = gray_drawerSelectedIconColor,
        drawerUnselectedIconColor = gray_drawerUnselectedIconColor,
        drawerFieldSelectedColor = gray_drawerFieldSelectedColor ,
        drawerFieldUnselectedColor = gray_drawerFieldUnselectedColor
    )
}
