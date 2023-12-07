package ru.betel.domain.model.ui

enum class Weekday(val day: String, var isSelected: Boolean) {
    Monday("Երկուշաբթի", false),
    Tuesday("Երեքշաբթի", false),
    Wednesday("Չորեքշաբթի", false),
    Thursday("Հինգշաբթի", false),
    Friday("Ուրբաթ", false),
    Saturday("Շաբաթ", false),
    Sunday("Կիրակի", true)
}