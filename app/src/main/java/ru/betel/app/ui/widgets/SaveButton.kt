package ru.betel.app.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.betel.app.R
import ru.betel.app.ui.theme.actionBarColor

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    btnColor: Color = actionBarColor,
    text: String = "Պահպանել",
    onClick: () -> Unit,
) {
    Box {
        Button(
            onClick = { onClick() },
            shape = RoundedCornerShape(size = 8.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = btnColor)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = fontSize,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(500),
                    color = if (btnColor != actionBarColor) Color.Black else Color(0xFFFFFFFF),
                )
            )
        }
    }
}

@Composable
fun SaveButton(text: String = "Պահպանել",onClick: () -> Unit) {
    Box {
        Button(
            onClick = { onClick() },
            shape = RoundedCornerShape(size = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = actionBarColor)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mardoto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                )
            )
        }
    }
}

