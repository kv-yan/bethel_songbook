package ru.betel.domain.model.ui

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

data class MenuItem(
    val title: String,
    val activeIcon: Int,
    val passiveIcon: Int,/*
    val activeTint: Color,
    val passiveTint: Color,*/
    var isSelected: MutableState<Boolean>,
    val screen: Screens,
)
