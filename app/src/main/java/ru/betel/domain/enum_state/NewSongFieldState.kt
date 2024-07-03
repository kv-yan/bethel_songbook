package ru.betel.domain.enum_state

import androidx.compose.ui.graphics.Color
import ru.betel.app.ui.theme.actionBarColor

enum class NewSongFieldState(val msg: String, val bgColor: Color) {
    INVALID_TITLE("Լրացրեք 'Վերնագիր' բաժինը", Color.Red),
    INVALID_WORDS("Լրացրեք 'Տեքստ' բաժինը", Color.Red),
    INVALID_TONALITY("Լրացրեք 'Տոն' բաժինը", Color.Red),
    INVALID_TEMP("Լրացրեք 'Տեմպ' բաժինը", Color.Red),
    INVALID_CATEGORY("Նշեք թէ երգը որ տեսակին է պատկանում․ \nԵրգը պոտք է լինի կամ 'փառաբանություն' կամ 'Երկրպագություն'", Color.Red),
    DONE("Երգը հաջողությամբ պահպանվել է", actionBarColor),
}
