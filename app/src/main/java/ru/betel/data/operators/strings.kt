package ru.betel.data.operators

operator fun String.minus(string: String): String {
    val newValue = this.removeSuffix(string)
    return newValue
}