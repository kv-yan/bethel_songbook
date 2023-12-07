package ru.betel.domain.model.ui


enum class SongsCategory(val title: String, var isSelected: Boolean) {
    ALL(title = "Բոլորը", isSelected = false),
    GLORIFYING(title = "Փառաբանություն", isSelected = true),
    WORSHIP(title = "Երկրպագություն", isSelected = false),
    GIFT(title = "Ընծա", isSelected = false),
    FROM_SONGBOOK(title = "Երգարանային", isSelected = false)
}