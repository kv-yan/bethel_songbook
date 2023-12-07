package ru.betel.domain.model.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

enum class SongTonality(val ton: String, var isSelected:MutableState<Boolean>) {
    `-12`("-12", mutableStateOf(false)),
    `-11`("-11", mutableStateOf(false)),
    `-10`("-10", mutableStateOf(false)),
    `-9`("-9", mutableStateOf(false)),
    `-8`("-8", mutableStateOf(false)),
    `-7`("-7", mutableStateOf(false)),
    `-6`("-6", mutableStateOf(false)),
    `-5`("-5", mutableStateOf(false)),
    `-4`("-4", mutableStateOf(false)),
    `-3`("-3", mutableStateOf(false)),
    `-2`("-2", mutableStateOf(false)),
    `-1`("-1", mutableStateOf(false)),
    `0`("0", mutableStateOf(true)),
    `12`("12", mutableStateOf(false)),
    `11`("11", mutableStateOf(false)),
    `10`("10", mutableStateOf(false)),
    `9`("9", mutableStateOf(false)),
    `8`("8", mutableStateOf(false)),
    `7`("7", mutableStateOf(false)),
    `6`("6", mutableStateOf(false)),
    `5`("5", mutableStateOf(false)),
    `4`("4", mutableStateOf(false)),
    `3`("3", mutableStateOf(false)),
    `2`("2", mutableStateOf(false)),
    `1`("1", mutableStateOf(false)),
}