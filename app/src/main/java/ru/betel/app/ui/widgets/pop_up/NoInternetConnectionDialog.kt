package ru.betel.app.ui.widgets.pop_up

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor


@Composable
fun NoInternetConnectionDialog(
    isShowDialog: MutableState<Boolean>
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.no_connection_anim))

    val onDismiss = { isShowDialog.value = !isShowDialog.value }
    if (isShowDialog.value) {
        AlertDialog(onDismissRequest = { onDismiss() }, buttons = {
            Surface(shape = RoundedCornerShape(15.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Lottie animation
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier.size(180.dp),
                        isPlaying = true,
                        restartOnPlay = true,
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No internet connection found",
                        style = TextStyle.Default.copy(fontSize = 18.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.widthIn(min = 150.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Check your connection and try again ",
                        style = TextStyle.Default.copy(fontSize = 14.sp, color = Color.Gray),
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(size = 8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = actionBarColor)
                    ) {
                        Text(
                            text = "Try Again", style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }
                    Text(text = "Skip",
                        style = TextStyle.Default.copy(fontSize = 14.sp, color = Color.Gray),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            onDismiss()
                        })
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }

        })
    }
}


