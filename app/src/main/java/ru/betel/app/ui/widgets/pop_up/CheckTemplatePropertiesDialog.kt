package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor


@Composable
fun CheckTemplatePropertiesDialog(
    isShowDialog: MutableState<Boolean>,
) {
    val isSelectedSelfVisibility = remember {
        mutableStateOf(
            false
        )
    }
    val isSendNotificationToAllUsers = remember {
        mutableStateOf(
            false
        )
    }

    val onDismiss = { isShowDialog.value = !isShowDialog.value }
    if (isShowDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, buttons = {
            Surface(shape = RoundedCornerShape(15.dp)) {
                Column(Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.clickable {
                        isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                    }) {
                        RadioButton(selected = isSelectedSelfVisibility.value,
                            onClick = {
                                isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                            })
                        Text(
                            text = "Տեսանելի միայն ինձ", style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                            ), modifier = Modifier.padding(start = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                        }) {
                        RadioButton(selected = !isSelectedSelfVisibility.value,
                            onClick = {
                                isSelectedSelfVisibility.value = !isSelectedSelfVisibility.value
                            })
                        Text(
                            text = "Տեսանելի բոլորին", style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                            ), modifier = Modifier.padding(start = 6.dp)
                        )
                    }

                    if (!isSelectedSelfVisibility.value) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .clickable {
                                    isSendNotificationToAllUsers.value =
                                        !isSendNotificationToAllUsers.value
                                }
                        ) {
                            Checkbox(
                                checked = isSendNotificationToAllUsers.value,
                                onCheckedChange = {
                                    isSendNotificationToAllUsers.value =
                                        !isSendNotificationToAllUsers.value
                                })
                            Text(
                                text = "Ուղարկել բոլորին ծանուցում", style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color.Black,
                                ), modifier = Modifier.padding(start = 6.dp)
                            )
                        }

                    }

                }
            }
        })
    }
}


@Preview
@Composable
fun CheckTemplatePropertiesPrev() {
    val isShowing = remember {
        mutableStateOf(
            false
        )
    }
    Text(text = "Open",
        Modifier
            .clickable { isShowing.value = !isShowing.value }
            .fillMaxSize(),
        textAlign = TextAlign.Center)

    CheckTemplatePropertiesDialog(isShowing)
}

