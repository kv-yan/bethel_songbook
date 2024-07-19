package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate

@Composable
fun SendNotificationDialog(
    showDialog: MutableState<Boolean>,
    template: MutableState<SongTemplate>,
    onConfirmationClick: (SongTemplate) -> Unit,
) {
    val onDismiss = { showDialog.value = false }
    if (showDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, title = {
            val dialogTitle = buildAnnotatedString {
                append("Ցանկանում էք տեղեկացնել բոլոր օգտատերերին ")
            }
            Text(text = dialogTitle, fontSize = 14.sp)
        }, text = {
            Text(text = "Եթէ ցանկանում եք , սեղմեք ՛Ուղարկել՛", fontSize = 14.sp)
        }, confirmButton = {
            Text("Ուղարկել", color = actionBarColor, modifier = Modifier.clickable {
                onConfirmationClick(template.value)
                onDismiss()
            })
        }, dismissButton = {
            Text("Չեղարկել", color = actionBarColor, modifier = Modifier.clickable {
                onDismiss()
            })
        })
    }
}