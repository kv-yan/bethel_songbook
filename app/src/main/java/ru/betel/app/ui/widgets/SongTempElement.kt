package ru.betel.app.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.betel.domain.model.ui.AppTheme


@Composable
fun TempElement(temp: MutableState<Float>, appTheme: AppTheme) {
    val isShowingTempDialog = remember { mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = appTheme.fieldBackgroundColor,
        modifier = Modifier
            .height(38.dp)
            .fillMaxWidth()
            .background(color = appTheme.fieldBackgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable {
                isShowingTempDialog.value = true
            },
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Տեմպ ՝ ${temp.value.toInt()}", color = appTheme.primaryTextColor)
        }
    }

    TempDialog(isShowingTempDialog = isShowingTempDialog, temp = temp, appTheme = appTheme)

}


@Composable
fun TempDialog(
    isShowingTempDialog: MutableState<Boolean>, temp: MutableState<Float>, appTheme: AppTheme
) {
    var showTextField by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(temp.value.toInt().toString()) }

    if (isShowingTempDialog.value) {
        Dialog(onDismissRequest = { isShowingTempDialog.value = false }) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = appTheme.fieldBackgroundColor,
                modifier = Modifier.clickable {
                    showTextField = false
                }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (showTextField) {
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                textFieldValue = newValue
                                newValue.toFloatOrNull()?.let { newTemp ->
                                    temp.value = newTemp
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp)
                        )
                    } else {
                        Text(text = "Ընտրեք տեմպ՝ ${temp.value.toInt()}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showTextField = true })
                    }

                    Slider(
                        colors = SliderDefaults.colors(
                            thumbColor = appTheme.primaryButtonColor,
                            activeTrackColor = appTheme.primaryButtonColor
                        ), value = temp.value, onValueChange = { newValue ->
                            temp.value = newValue
                            textFieldValue = newValue.toInt().toString()
                        }, valueRange = 20f..250f, modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

        }
    }
}
