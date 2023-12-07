package ru.betel.domain.model.ui

import androidx.compose.runtime.MutableState

data class MenuItem(
    val title: String,
    val activeIcon: Int,
    val passiveIcon: Int,
    var isSelected: MutableState<Boolean>,
    val screen: Screens,
)
