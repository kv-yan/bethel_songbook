package ru.betel.app.ui.widgets.loading_anim

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingPopUp(
    isShowDialog: MutableState<Boolean>,
) {
    val onDismiss = { isShowDialog.value = !isShowDialog.value }
    if (isShowDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() },
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp, vertical = 250.dp),
            buttons = {
                Surface(shape = RoundedCornerShape(15.dp), color = Color.White) {
                    LoadingScreen()
                }
            }, properties = DialogProperties(dismissOnBackPress = false)
        )
    }
}


