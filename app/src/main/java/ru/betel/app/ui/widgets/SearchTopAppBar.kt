package ru.betel.app.ui.widgets

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor
import ru.betel.app.ui.theme.fieldBg
import ru.betel.domain.model.ui.SongbookTextSize
import ru.betel.domain.model.ui.TrailingIconState

@Composable
fun SearchTopAppBar(
    text: MutableState<String>,
    textSize: SongbookTextSize,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    BackHandler {
        onCloseClicked()
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.DELETE)
    }

    MyTextFields(fieldText = text,
        singleLine = true,
        fontSize = textSize.textFieldItemDefaultTextSize,
        placeholder = "Որոնում",
        leadingIcon = {
            Row {
                Icon(painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = actionBarColor,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                        .clickable {})

                Spacer(modifier = Modifier.width(10.dp))

            }

        },
        trailingIcon = {

            IconButton(onClick = {
                println("trailingIconState : $trailingIconState")
                when (trailingIconState) {
                    TrailingIconState.DELETE -> {
                        println()
                        trailingIconState = if (text.value.isNotEmpty()) {
                            println("trailingIconState if block ")
                            text.value = ""
                            onTextChange("")
                            TrailingIconState.CLOSE
                        } else {
                            println("trailingIconState else block ")
                            onCloseClicked()
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            TrailingIconState.DELETE
                        }
                    }

                    TrailingIconState.CLOSE -> {
                        if (text.value.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                            trailingIconState = TrailingIconState.DELETE
                        }
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = actionBarColor,
                    modifier = Modifier.size(12.dp)
                )
            }
        })
}