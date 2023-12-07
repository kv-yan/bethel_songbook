package ru.betel.domain.model.ui


enum class TemplateType(val title: String, var isSelected: Boolean = false) {
    ALL(title = "Բոլորը"),
    MINE(title = "Իմը"),
}