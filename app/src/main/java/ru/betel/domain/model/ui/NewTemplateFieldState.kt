package ru.betel.domain.model.ui

import androidx.compose.ui.graphics.Color
import ru.betel.app.ui.theme.actionBarColor

enum class NewTemplateFieldState(val msg: String, val bgColor: Color) {
    INVALID_NAME("Լրացրեք 'Առաջնորդ' բաժինը", Color.Red),
    INVALID_GLORIFYING("Լրացրեք 'Փառաբանություն' բաժինը", Color.Red),
    INVALID_WORSHIP("Լրացրեք 'Երկրպագություն' բաժինը", Color.Red),
    INVALID_GIFT("Լրացրեք 'Ընծա' բաժինը", Color.Red),
    INVALID_WEEKDAY("Լրացրեք 'Շաբարվա օր' բաժինը", Color.Red),
    INVALID_DAY("Լրացրեք 'Ամսաթիվ' բաժինը", Color.Red),
    DONE("Ցուցակը հաջողությամբ պահպանվել է", actionBarColor),
}
