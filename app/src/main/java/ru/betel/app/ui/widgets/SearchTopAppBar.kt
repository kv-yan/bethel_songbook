package ru.betel.app.ui.widgets

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.betel.app.R
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.SongbookTextSize
import ru.betel.domain.model.ui.TrailingIconState

@Composable
fun SearchTopAppBar(
    appTheme: AppTheme = AppTheme.GRAY,
    text: MutableState<String>,
    textSize: SongbookTextSize,
    isInBottomSheet: Boolean = false,
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

    MyTextFields(isForSearch = true,
        singleLine = true,
        fieldText = text,
        fontSize = textSize.textFieldItemDefaultTextSize,
        placeholder = " Որոնում",
        leadingIcon = {
            Row {
                Icon(painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = appTheme.actionBarColor,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                        .clickable {})
            }
        },
        trailingIcon = {
            if (!isInBottomSheet) {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.DELETE -> {
                            println()
                            trailingIconState = if (text.value.isNotEmpty()) {
                                text.value = ""
                                onTextChange("")
                                TrailingIconState.CLOSE
                            } else {
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
                        tint = appTheme.actionBarColor,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        })
}