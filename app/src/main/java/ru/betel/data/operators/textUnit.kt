package ru.betel.data.operators

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

operator fun TextUnit.plus(textUnit: Float): TextUnit {
    val newValue: Float = this.value + textUnit
    return newValue.sp
}