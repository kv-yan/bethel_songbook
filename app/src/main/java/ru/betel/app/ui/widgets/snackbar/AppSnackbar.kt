package ru.betel.app.ui.widgets.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppSnackbar(
    isShowing: MutableState<Boolean>, modifier: Modifier, content: @Composable () -> Unit
) {

    AnimatedVisibility(
        visible = isShowing.value,
        modifier = modifier,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -180 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -50 }),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            content.invoke()
        }
    }


    LaunchedEffect(isShowing.value) {
        if (isShowing.value) {
            delay(2500)
            isShowing.value = false
        }
    }
}

