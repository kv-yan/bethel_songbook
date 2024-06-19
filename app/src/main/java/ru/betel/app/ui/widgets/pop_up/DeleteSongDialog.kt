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

@Composable
fun DeleteSongDialog(
    showDialog: MutableState<Boolean>,
    song: MutableState<Song>,
    onConfirmationClick: (Song) -> Unit,
    onUpdateSongs: () -> Unit
) {
    val onDismiss = { showDialog.value = false }
    if (showDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, title = {
            val dialogTitle = buildAnnotatedString {
                append("Իսկապես ցանկանում էք ջնջել '")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append(song.value.title)
                pop()
                append("' երգը ?")
            }
            Text(text = dialogTitle, fontSize = 14.sp)
        }, text = {

        }, confirmButton = {
            Text("Ջնջել", color = actionBarColor, modifier = Modifier.clickable {
                onConfirmationClick(song.value)
                onUpdateSongs()
                onDismiss()
            })
        }, dismissButton = {
            Text("Չեղարկել", color = actionBarColor, modifier = Modifier.clickable {
                onDismiss()
            })
        })
    }
}