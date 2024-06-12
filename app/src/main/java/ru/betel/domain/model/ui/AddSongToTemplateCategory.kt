package ru.betel.domain.model.ui

sealed class AddSongToTemplateCategory() {
    sealed class Glorifying : AddSongToTemplateCategory()
    sealed class Worship : AddSongToTemplateCategory()
    sealed class Gift : AddSongToTemplateCategory()
}
