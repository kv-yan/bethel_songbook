package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.domain.model.SongTemplate

@Composable
fun DeleteTemplateDialog(
    mode: TemplateSaveMode,
    showDialog: MutableState<Boolean>,
    template: MutableState<SongTemplate>,
    onConfirmationClick: (SongTemplate, TemplateSaveMode) -> Unit,
    onUpdateSongs: () -> Unit
) {
    val onDismiss = { showDialog.value = false }
    if (showDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, title = {
            val dialogTitle = buildAnnotatedString {
                append("Իսկապես ցանկանում էք ջնջել '")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append(template.value.createDate)
                pop()
                append("' օրվա ցուցակը?")
            }
            Text(text = dialogTitle, fontSize = 14.sp)
        }, text = {
            val text = buildAnnotatedString {
                append("Ջնջելու դեպքում այն կջնջվի ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic))
                append(
                    when (mode) {
                        TemplateSaveMode.SERVER -> "ԲՈԼՈՐԻ"
                        TemplateSaveMode.LOCAL -> "ՄԻԱՅՆ ՔԵԶ"
                    }
                )
                pop()
                append(" մոտ և հնարավոր չի լինի այն վերականգնել․")
            }

            Text(text = text)

        }, confirmButton = {
            Text("Ջնջել", color = actionBarColor, modifier = Modifier.clickable {
                onConfirmationClick(template.value, mode)
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